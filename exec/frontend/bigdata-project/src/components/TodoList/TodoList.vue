<template>
  <div class="todolist-container">
    <div>
      <div class="todo-app" id="todo-app">
        <span class="todo_title">오늘 {{ childName }}의 할 일</span>
        <span class="today_date">{{ date }}</span>
        <form v-if="loginType === '1'" class="form" v-on:submit="addTodo">
          <input class="input form__input" v-model="inputVal" />
          <button class="btn form__submit-btn" type="submit">추가</button>
        </form>
        <transition-group tag="ol" name="list" class="todo-list">
          <li
            class="todo-list__item"
            v-bind:class="{ check: todo.todo_check }"
            v-bind:key="index"
            v-for="(todo, index) in filteredTodos"
          >
            <button
              class="todo-list__item-content"
              v-on:click="toggleTodo(todo)"
            >
              {{ todo.todo_content }}
            </button>
            <button
              class="btn todo-list__item-remove"
              v-on:click="deleteTodo(todo, index)"
            >
              <i
                class="fa"
                v-bind:class="[todo.todo_check ? 'fa-check' : 'fa-times']"
              ></i>
            </button>
          </li>
        </transition-group>
        <div class="filters">
          <div class="todolist-carrot">
            <button
              class="btn filters__btn filters__btn--complete"
              v-on:click="filterTodos('complete')"
            >
              모두 완료
            </button>
            <div class="leaves-1"></div>
            <div class="leaves-2"></div>
            <div class="leaves-3"></div>
          </div>
        </div>
      </div>
    </div>
    <MyPageTab v-if="loginType === '1'" @update="movepath" />
  </div>
</template>

<script>
import http from "@/api/http-common";
import MyPageTab from "../MyPage/MyPageTab";
var filters = {
  all: function (todos) {
    return todos;
  },
  complete: function (todos) {
    return todos.filter(function (todo) {
      return todo.todo_check;
    });
  },
  incomplete: function (todos) {
    return todos.filter(function (todo) {
      return !todo.todo_check;
    });
  },
};

export default {
  components: {
    MyPageTab: MyPageTab,
  },
  data() {
    return {
      modal: false,
      contentbydate: [],
      content: [],
      state: [],
      nowdate: "",
      // todo
      allTodoList: [],
      inputVal: "",
      todos: this.fetchTodoStorage(),
      visibility: "all",
      childName: "",
      // isToday: true,
      today: "",
      date: "",
      // 1: parent, 2: baby
      loginType: "", 
    };
  },
  computed: {
    filteredTodos: function () {
      return filters[this.visibility](this.todos);
    },
  },
  created() {
    this.getAllTodoList();
    this.getMyChildName();
    var today_date = new Date();
    this.today = String(this.dateFormat(today_date));
  },
  mounted() {
    // this.saveTodoStorage();
    this.loginType = localStorage.getItem("loginType");
  },
  updated() {
    this.saveTodoStorage();
  },
  methods: {
    getMyChildName() {
      http
        .get("/child/getMyChild", {
          params: {
            child_id: localStorage.getItem("child_id"),
          },
        })
        .then((res) => {
          this.childName = res.data.child_name;
        });
    },
    movepath(event) {
      if (this.$route.name === event) {
        window.location.reload();
      } else {
        this.$router.push({ name: event });
      }
    },
    saveTodoStorage() {
      var child_id = String(localStorage.getItem("child_id"));
      const STORAGE_KEY = "vue-js-todo-" + child_id;

      localStorage.setItem(STORAGE_KEY, JSON.stringify(this.todos));
    },
    fetchTodoStorage() {
      var child_id = String(localStorage.getItem("child_id"));
      const STORAGE_KEY = "vue-js-todo-" + child_id;

      var todos = JSON.parse(localStorage.getItem(STORAGE_KEY) || "[]");
      return todos;
    },
    getAllTodoList() {
      http
        .get("/todo/getMyChildTodoList", {
          params: {
            todo_childId: localStorage.getItem("child_id"),
            todo_parentId: localStorage.getItem("parent_id"),
          },
        })
        .then((response) => {
          var getTodoList = response.data;
          for (var i = 0; i < getTodoList.length; i++) {
            const date = getTodoList[i].todo_date.slice(0, 10);

            this.allTodoList.push({
              todo_id: getTodoList[i].todo_id,
              todo_date: date,
              todo_content: getTodoList[i].todo_content,
              todo_check: getTodoList[i].todo_check,
            });
          }

          this.getTodayTodoList();
        });
    },
    getTodayTodoList() {
      const today_filter = this.allTodoList.filter((it) =>
        it.todo_date.includes(this.today)
      );

      for (var i = 0; i < today_filter.length; i++) {
        const today_todo = today_filter[i];

        const len = this.todos.filter((it) =>
          it.todo_id.includes(today_todo.todo_id)
        ).length;
        if (len == 0) {
          this.todos.push({
            todo_id: today_todo.todo_id,
            todo_content: today_todo.todo_content,
            todo_check: today_todo.todo_check,
          });
        } else {
          this.todos[i].todo_check = today_todo.todo_check;
        }
      }
      this.date = this.today;
    },
    dateFormat(date) {
      return (
        date.getFullYear() +
        "-" +
        ("0" + (date.getMonth() + 1)).slice(-2) +
        "-" +
        ("0" + date.getDate()).slice(-2)
      );
    },
    addTodo(e) {
      e.preventDefault();

      if (this.inputVal) {
        const child_id = localStorage.getItem("child_id");
        const parent_id = localStorage.getItem("parent_id");

        var formData = new FormData();
        formData.append("todo_childId", child_id);
        formData.append("todo_content", this.inputVal);
        formData.append("todo_parentId", parent_id);

        http
          .post("/todo/addTodo", formData)
          .then((response) => {
            this.todos.push({
              todo_id: response.data.state,
              todo_content: formData.get("todo_content"),
              todo_check: false,
            });
          })
          .catch((err) => {
            console.error(err);
          });
      }

      this.inputVal = "";

      const todo_list = document.querySelector(".todo-list");
      var location = todo_list.scrollHeight;
      todo_list.scrollTo({ top: location, behavior: "smooth" });
    },
    toggleTodo: function (todo) {
      todo.todo_check = !todo.todo_check;

      var formData = new FormData();
      formData.append("todo_id", todo.todo_id);

      http.post("/todo/checkTodo", formData).then(() => {});
    },
    filterTodos: function (filter) {
      this.visibility = filter;

      var formData = new FormData();
      formData.append("todo_childId", localStorage.getItem("child_id"));

      if (filter == "complete") {
        http.post("/todo/completeTodayTodo", formData).then(() => {});
      } else {
        http.post("/todo/incompleteTodayTodo", formData).then(() => {});
      }
    },
    deleteTodo: function (todo, index) {
      this.todos.splice(index, 1);

      var formData = new FormData();
      formData.append("todo_id", todo.todo_id);

      http.post("/todo/removeTodo", formData).then(() => {});
    },
    deleteDaysTodo(daytodo) {
      var formData = new FormData();
      formData.append("todo_childId", localStorage.getItem("child_id"));
      formData.append("todo_parentId", localStorage.getItem("parent_id"));

      formData.append("todo_date", daytodo[0].datebystring);
      http.post("/todo/removeDaysTodo", formData);
      window.location.reload();
    },
  },
};
</script>

<style>
.todolist-container {
  width: 100vw;
  /* margin-top: 30%; */
  height: 100vh;
  overflow: hidden;
}

/* todo */
*,
*:before,
*:after {
  box-sizing: border-box;
}

html {
  overflow-y: scroll;
  overflow-x: hidden;
  height: 100%;
  background-image: url("../../assets/img/bg/bg11.png");
  background-size: cover;
}

/* body {
  overflow: hidden;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-family: "Source Sans Pro", sans-serif;
} */

button {
  background: none;
  width: 25%;
  border: none;
  color: inherit;
  font-size: inherit;
  font-family: inherit;
  font-weight: inherit;
}
button:focus {
  outline: none;
}
button:hover {
  cursor: pointer;
}

.todo-app {
  min-height: 10vh;
  max-height: 73vh;
  margin-left: 1.5vw;
  margin-right: 1.5vw;
  margin-top: 11vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
  border-radius: 1em;
  background: #fff;
  overflow: hidden;
  box-shadow: 0 0 5px rgba(25, 25, 25, 0.25);
}

.btn {
  font-size: 14px;
  margin: 0 0.5em;
  border-radius: 2em;
  padding: 0.75em 1.5em;
  cursor: pointer;
  background: none;
  color: #197c4e;
  border: 1px solid;
  letter-spacing: 1px;
  font-weight: bold;
  color: #40aa57;
  border: #40aa57 1px solid;
  transition: 250ms ease-out;
}
.btn:hover,
.btn:focus {
  color: #fff;
  background: #40aa57;
}

.form {
  width: 100%;
  padding: 1.5rem 1rem 0 1rem;
  display: flex;
  margin-bottom: 4%;
}
.form__input {
  width: 70%;
  font-size: 14px;
  margin: 0 0.5em;
  border-radius: 2em;
  padding: 0.75em 1.5em;
  background: none;
  font-family: "Source Sans Pro", sans-serif;
  border: #e3e3e3 1px solid;
  transition: border 250ms ease-out;
}
/* ===== */
.fa,
.fab,
.fad,
.fal,
.far,
.fas {
  font-weight: 900 !important;
}
.form__input:focus {
  border: #40aa57 1px solid;
  outline: none;
}
.todo-list {
  width: 100%;
  padding: 0 1rem;
  flex: 1;
  overflow-y: scroll;
  overflow-x: hidden;
}
.todo-list__item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0.5em;
  margin-bottom: 0.5em;
  border-radius: 3px;
  transition: 200ms;
  color: #40aa57;
}
.todo-list__item:last-child {
  margin-bottom: 0;
}
.todo-list__item.check {
  color: #f8b155;
}
.todo-list__item.check .todo-list__item-content:after {
  background: #f8b155;
}
.todo-list__item-content {
  position: relative;
  text-align: left;
  width: 80%;
}
.todo-list__item-content:after {
  content: "";
  position: absolute;
  top: 100%;
  /* left: 0;
  right: 0; */
  height: 1px;
  background: #40aa57;
  transition: 250ms ease-out;
  transform-origin: center;
  transform: scalex(0);
}
.todo-list__item-content:hover:after,
.todo-list__item-content:focus:after {
  transform: scalex(1);
}
.todo-list__item-remove {
  margin-left: 0.5em;
  background: none;
  border: 1px solid;
  color: inherit;
  padding: 0;
  line-height: 1;
  width: 2em;
  height: 2em;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 50%;
  font-size: 80%;
}

.filters {
  width: 100%;
  margin-top: 3%;
  padding-bottom: 1rem;
  padding-right: 5%;
}
.filters .todolist-carrot{
  width: 50%;
  max-width: 170px;
  margin:auto;
  display: flex;
  justify-content: center;
  transition: 0.25s cubic-bezier(0.25, 0, 0, 1);
}
.filters .todolist-carrot:active {
  transform: translateY(5%);
}
.filters .todolist-carrot .filters__btn--complete {
  font-size: 20px;
  width: 80%;
  height: 40px;
  position: relative;
  background: #f3951b;
  border: #f3951b 1px solid;
  color:white;
  max-width: 180px;
  border-radius: 80% 20px 20px 80%;
  z-index: 10;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 0 0 0 10%;
  margin: 0;
}
.filters .todolist-carrot .leaves-1,
.filters .todolist-carrot .leaves-2,
.filters .todolist-carrot .leaves-3{
  position: absolute;
  width: 5px;
  z-index: 0;
  height: 5px;
  border-right: 40px solid #40aa57;
  border-bottom: 10px solid transparent;
  border-radius: 50%;
  right: -8%;
}
.filters .todolist-carrot .leaves-1{
  transform: rotate(-30deg);
  top:8%;
}
.filters .todolist-carrot .leaves-2{
  top:33%;
}
.filters .todolist-carrot .leaves-3{
  transform: rotate(30deg);
  top:58%;
}

.list-move,
.list-leave-active,
.list-enter-active {
  transition: 500ms cubic-bezier(0.87, -0.41, 0.19, 1.44);
}

.list-enter,
.list-leave-active {
  transform: translate(100%, 0);
  opacity: 0;
}
.today_date {
  margin-top: 5%;
  font-size: 25px;
  font-weight: bold;
  color: #40aa57;
}
.todo_title {
  margin-top: 5%;
  font-size: 30px;
  color: #f3951b;
}
</style>

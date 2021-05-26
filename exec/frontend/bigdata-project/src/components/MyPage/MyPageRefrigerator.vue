<template>
  <div class="mypagerefrigerator-container">
    <div>
      <div class="refrigerator-app" id="refrigerator-app">
        <span class="r_title"> 냉장고 재료 등록</span>
        <form class="form" v-on:submit="addRefrigerator">
          <input class="input form__input" v-model="refrigeratorName" />
          <button class="btn form__submit-btn" type="submit">추가</button>
        </form>
        <transition-group tag="ol" name="list" class="refrigerator-list">
          <li
            class="refrigerator-list__item"
            v-bind:key="index + 0"
            v-for="(refrigerator, index) in refrigeratorList"
          >
            <button class="refrigerator-list__item-content">
              {{ refrigerator }}
            </button>
            <button
              class="btn refrigerator-list__item-remove"
              v-on:click="deleteRefrigerator(refrigerator)"
            >
              <i class="fa" v-bind:class="['fa-times']"></i>
            </button>
          </li>
        </transition-group>
      </div>
    </div>
    <MyPageTab @update="movepath" />
  </div>
</template>

<script>
import http from "@/api/http-common";
import MyPageTab from "./MyPageTab";
export default {
  components: {
    MyPageTab: MyPageTab,
  },
  data() {
    return {
      refrigeratorId: "",
      refrigeratorName: "",
      refrigeratorList: null,
    };
  },
  methods: {
    getRefrigeratorId() {
      http
        .get("/refrigerator/getMyRefrigeratorId", {
          params: {
            refrigerator_parentsId: localStorage.getItem("parent_id"),
          },
        })
        .then((res) => {
          this.refrigeratorId = res.data;
          this.getRefrigeratorInfo();
        });
    },
    addRefrigerator(e) {
      e.preventDefault();
      var formData = new FormData();

      formData.append("refrigeratorDetail_refrigeratorId", this.refrigeratorId);
      formData.append(
        "refrigeratorDetail_ingredientName",
        this.refrigeratorName
      );

      http.post("/refrigerator/addMyRefrigerator", formData).then(() => {
        this.getRefrigeratorInfo();
      });

      this.refrigeratorName = "";

      const refrigerator_list = document.querySelector(".refrigerator-list");
      var location = refrigerator_list.scrollHeight;
      refrigerator_list.scrollTo({ top: location, behavior: "smooth" });
    },
    getRefrigeratorInfo() {
      http
        .get(`/refrigerator/getMyRefrigerator/${this.refrigeratorId}`)
        .then((res) => {
          this.refrigeratorList = res.data;
        });
    },
    deleteRefrigerator(refrigerator) {
      http
        .delete("/refrigerator/removeMyRefrigerator", {
          params: {
            refrigeratorDetail_refrigeratorId: this.refrigeratorId,
            refrigeratorDetail_ingredientName: refrigerator,
          },
        })
        .then(() => {
          this.getRefrigeratorInfo();
        });
    },
    movepath(event) {
      if (this.$route.name === event) {
        window.location.reload();
      } else {
        this.$router.push({ name: event });
      }
    },
  },
  mounted() {
    this.getRefrigeratorId();
  },
};
</script>

<style scope>
.fa,
.fab,
.fad,
.fal,
.far,
.fas {
  font-weight: 900 !important;
}
.mypagerefrigerator-container {
  width: 100vw;
  /* margin-top: 30%; */
  height: 100vh;
  overflow: hidden;
}

button {
  background: none;
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

*,
*:before,
*:after {
  box-sizing: border-box;
}

.refrigerator-app {
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

.form {
  width: 100%;
  padding: 1.5rem 1rem 1rem 1rem;
  display: flex;
  margin-bottom: 4%;
}

.form__input {
  width: 100%;
  font-size: 14px;
  margin: 0 0.5em;
  border-radius: 2em;
  padding: 0.75em 1.5em;
  background: none;
  font-family: "Source Sans Pro", sans-serif;
  border: #e3e3e3 1px solid;
  transition: border 250ms ease-out;
}

.form__input:focus {
  border: #40aa57 1px solid;
  outline: none;
}

button {
  background: none;
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

.refrigerator-list {
  width: 100%;
  padding: 0 1rem;
  flex: 1;
  overflow-y: scroll;
  overflow-x: hidden;
}

.refrigerator-list__item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0.5em;
  margin-bottom: 0.5em;
  border-radius: 3px;
  transition: 200ms;
  color: #40aa57;
}
.refrigerator-list__item:last-child {
  margin-bottom: 3%;
}
.refrigerator-list__item.check {
  color: #40aa57;
}
.refrigerator-list__item.check .todo-list__item-content:after {
  background: #40aa57;
}
.refrigerator-list__item-content {
  position: relative;
  text-align: left;
}
.refrigerator-list__item-content:after {
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
.refrigerator-list__item-content:hover:after,
.refrigerator-list__item-content:focus:after {
  transform: scalex(1);
}

.refrigerator-list__item-remove {
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

.r_title {
  float: left;
  margin-top: 5%;
  font-size: 30px;
  color: #f3951b;
}
</style>
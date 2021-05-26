<template>
  <div class="childallergy-container">
    <div>
      <div class="allergy-app" id="allergy-app">
        <span class="allergy_title">{{ childName }}의 알러지</span>
        <form class="form" v-on:submit="addAllergy">
          <input class="input form__input" v-model="allergyName" />
          <button class="btn form__submit-btn" type="submit">추가</button>
        </form>
        <transition-group tag="ol" name="list" class="allergy-list">
          <li
            class="allergy-list__item"
            v-bind:key="idx + 0"
            v-for="(allergy, idx) in allergyList"
          >
            <button class="allergy-list__item-content">
              {{ allergy }}
            </button>
            <button
              class="btn allergy-list__item-remove"
              v-on:click="deleteAllergy(allergy)"
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
      modal: false,
      allergyId: "",
      allergyName: "",
      allergyList: null,
      childName: "",
    };
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
    // getAllergyId() {
    //   http
    //     .get("/allergy/getMyChildAllergyId", {
    //       params: {
    //         allergy_childId: localStorage.getItem("child_id"),
    //       },
    //     })
    //     .then((res) => {
    //       this.allergyId = res.data;
    //       this.getAllergyInfo();
    //     });
    // },
    addAllergy(e) {
      e.preventDefault();

      var formData = new FormData();
      formData.append(
        "allergyDetail_childId",
        localStorage.getItem("child_id")
      );
      formData.append("allergyDetail_allergyName", this.allergyName);

      http.post("/allergy/addMyChildAllergy/", formData).then(() => {
        this.getAllergyInfo();
      });

      this.allergyName = "";

      const allergy_list = document.querySelector(".allergy-list");
      var location = allergy_list.scrollHeight;
      allergy_list.scrollTo({ top: location, behavior: "smooth" });
    },
    getAllergyInfo() {
      http
        .get("/allergy/getMyChildAllergy/", {
          params: {
            allergyDetail_childId: localStorage.getItem("child_id"),
          },
        })
        .then((res) => {
          this.allergyList = res.data;
        });
    },
    deleteAllergy(allergy) {
      http
        .delete("/allergy/removeMyChildAllergy", {
          params: {
            allergyDetail_childId: localStorage.getItem("child_id"),
            allergyDetail_allergyName: allergy,
          },
        })
        .then(() => {
          this.getAllergyInfo();
        });
    },
    movepath(event) {
      if (this.$route.name === event) {
        window.location.reload();
      } else {
        this.$router.push({ name: event });
      }
    },
    onImages() {},
  },
  created() {
    this.getAllergyInfo();
    // this.getAllergyId();
    this.getMyChildName();
  },
  mounted() {},
};
</script>

<style scope>
.childallergy-container {
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

.allergy-app {
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
  color: #2d7c58;
  border: 1px solid;
  letter-spacing: 1px;
  font-weight: bold;
  color: #40aa57;
  border: #40aa57 1px solid;
  transition: 250ms ease-out;
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

.allergy-list {
  width: 100%;
  padding: 0 1rem;
  flex: 1;
  overflow-y: scroll;
  overflow-x: hidden;
}

.allergy-list__item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0.5em;
  margin-bottom: 0.5em;
  border-radius: 3px;
  transition: 200ms;
  color: #40aa57;
}
.allergy-list__item:last-child {
  margin-bottom: 3%;
}
.allergy-list__item.check {
  color: #40aa57;
}
.allergy-list__item.check .allergy-list__item-content:after {
  background: #40aa57;
}
.allergy-list__item-content {
  position: relative;
  text-align: left;
}
.allergy-list__item-content:after {
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
.allergy-list__item-content:hover:after,
.allergy-list__item-content:focus:after {
  transform: scalex(1);
}

.allergy-list__item-remove {
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

.al-list-move,
.al-list-leave-active,
.al-list-enter-active {
  transition: 500ms cubic-bezier(0.87, -0.41, 0.19, 1.44);
}

.al-list-enter,
.al-list-leave-active {
  transform: translate(100%, 0);
  opacity: 0;
}
.allergy_title {
  float: left;
  margin-top: 5%;
  font-size: 30px;
  color: #f3951b;
}
</style>

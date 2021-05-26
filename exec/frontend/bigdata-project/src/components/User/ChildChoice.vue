<template>
  <div class="childchoice">
    <div class="childchoice_container">
      <div class="childchoice_menu" style="color:#f3951b; font-size: 30px; -webkit-text-stroke: 0.7px white;">
        아이를 선택하세요!
      </div>
      <div class="childchoice_content">
        <div class="exist_child" v-if="child_list.length != 0">
          <div
            class="childchoice_children choice_box"
            v-for="(child, idx) in child_list"
            :key="idx"
            :id="child.child_id"
            @click="childChoice"
          >
            <span :id="child.child_id">{{ child.child_name }}<p style="font-size:13px; padding-top:5%; color:#40aa57;">{{ child.child_id }}</p></span>
          </div>
          <div class="childchoice_children add_box" @click="addChild">
            <span @click="addChild">+</span>
          </div>
        </div>
        <div class="childchoice_children" v-else @click="addChild">
          <span @click="addChild">+</span>
        </div>
      </div>
    </div>
    <div class="childchoice_info">
        * 아이디 값은 아이 이름 밑에 명시되어 있습니다. *
      </div>
  </div>
</template>

<script>
import "@/assets/css/User/ChildChoice.css";
import http from "@/api/http-common.js";
import router from "../../router";

export default {
  data() {
    return {
      parent_id: "",
      child_list: [],
      child_id: "",
    };
  },
  created() {
    // this.parent_id = "parent-1";
    this.parent_id = localStorage.getItem("parent_id");

    this.getChildList();
  },
  methods: {
    getChildList() {
      http
        .get(`child/getMyChildren`, {
          params: {
            child_parentId: this.parent_id,
          },
        })
        .then(
          (res) => {
            let data = res.data;
            for (var i = 0; i < data.length; i++) {
              if (data[i].child_profile != "") {
                data[i].child_profile = "data:image/jpeg;";
              }
            }
            this.child_list = res.data;
          },
          (err) => {
            console.log(err);
          }
        );
    },
    childChoice(e) {
      const checked = document.querySelector(".check_child");
      if (checked != null) {
        checked.classList.remove("check_child");
      }

      e.target.classList.add("check_child");
      this.child_id = e.target.id;

      localStorage.setItem("child_id", this.child_id);

      router.push({
        name: "MainPage",
      });
    },
    addChild() {
      router.push({
        name: "AddChild",
      });
    },
  },
};
</script>
<style scoped>
.childchoice_info {
  text-align: center;
}
</style>

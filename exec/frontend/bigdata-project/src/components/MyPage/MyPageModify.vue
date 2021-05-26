<template>
  <div class="MypageModify-container">
    <div class="container">
      <div id="profileform">
        <div>
          <input type="text" placeholder="이름" v-model="parent.parent_name" />
        </div>
        <div>
          <input
            type="text"
            placeholder="이메일"
            v-model="parent.parent_email"
          />
        </div>
        <div>
          <input
            type="password"
            placeholder="비밀번호(수정가능)"
            v-model="parent.parent_pwd"
          />
        </div>
        <div>
          <input type="text" placeholder="연락처" v-model="parent.parent_tel" />
        </div>
        <div>
          <input
            type="text"
            placeholder="생년월일"
            v-model="parent.parent_birth"
          />
        </div>
        <div id="button">
          <button id="btn" @click="modifyParent()">수정</button>
          <!-- <button id="btn" @click="leaveParent()">탈퇴</button> -->
        </div>
      </div>
    </div>
    <!-- <MyPageChildInfo /> -->
    <!-- <ChildAllergy />
    <ChildPreference /> -->
    <MyPageTab @update="movepath" />
  </div>
</template>

<script>
import http from "@/api/http-common";
// import ChildAllergy from "./ChildAllergy.vue";
// import ChildPreference from "./ChildPreference.vue";
// import MyPageChildInfo from "./MyPageChildInfo";
import MyPageTab from "./MyPageTab";
import "@/assets/css/MyPage/MyPageModify.css"

// import VueJwtDecode from "vue-jwt-decode"

export default {
  components: {
    // MyPageChildInfo: MyPageChildInfo,
    // ChildAllergy: ChildAllergy,
    // ChildPreference: ChildPreference,
    "MyPageTab": MyPageTab,
  },
  data() {
    return {
      parent: {
        parent_email: "",
        parent_pwd: "",
        parent_name: "",
        parent_tel: "",
        parent_birth: "",
      },
    };
  },
  mounted() {
    // this.getParentInfo();
  },
  created() {
    this.getParentInfo();
  },
  methods: {
    getParentInfo() {
      http
        .get("/parent/getParentInfo", {
          params: {
            parent_id: localStorage.getItem("parent_id"),
          },
        })
        .then((res) => {
          this.parent.parent_email = res.data.parent_email;
          this.parent.parent_name = res.data.parent_name;
          this.parent.parent_pwd = res.data.parent_pwd;
          this.parent.parent_tel = res.data.parent_tel;
          this.parent.parent_birth = res.data.parent_birth;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    modifyParent() {
      const formData = new FormData();
      formData.append("parent_birth", this.parent.parent_birth);
      formData.append("parent_id", localStorage.getItem("parent_id"));
      formData.append("parent_name", this.parent.parent_name);
      formData.append("parent_pwd", this.parent.parent_pwd);
      formData.append("parent_tel", this.parent.parent_tel);
      http.post("/parent/modifyParentInfo", formData).then(
        () => {
          alert("회원정보 수정 완료!");
        },
        (error) => {
          console.log(error);
        }
      );
    },
    movepath (event) {
      if (this.$route.name === event) {
        window.location.reload()
      } else {
        this.$router.push({name:event})
      }
    }
  },
};
</script>

<style>

</style>
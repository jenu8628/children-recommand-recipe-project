<template>
  <div class="login">
    <ul class="login_tabs">
      <li class="active" @click="toggleTab">부모</li>
      <li class="no_active" @click="toggleTab">아이</li>
    </ul>
    <div class="login_container">
      <div class="login_card"></div>
      <div class="login_card content_card show_content">
        <h1 class="title">부모님 로그인</h1>
        <div>
          <div class="input-container">
            <input
              type="text"
              id="input_email"
              required="required"
              v-model="info.email"
              @keyup.enter="logIn"
            />
            <label for="input_email">이메일</label>
            <div class="bar"></div>
          </div>
          <div class="input-container">
            <input
              type="password"
              id="input_pwd"
              required="required"
              @keyup.enter="logIn"
              v-model="info.password"
            />
            <label for="input_pwd">비밀번호</label>
            <div class="bar"></div>
          </div>
          <div class="button-container">
            <div class="button-box">
              <div class="carrot" @click="logIn"><span>로그인</span></div>
              <div class="leaves-1"></div>
              <div class="leaves-2"></div>
              <div class="leaves-3"></div>
            </div>
          </div>
          <br />
          <div class="button-container">
            <div class="button-box">
              <div class="carrot" @click="Join"><span>회원가입</span></div>
              <div class="leaves-1"></div>
              <div class="leaves-2"></div>
              <div class="leaves-3"></div>
            </div>
          </div>
          <!-- <div class="footer"><a href="#">Forgot your password?</a></div> -->
        </div>
        <!-- 아이디 찾기 비밀번호 찾기 -->
        <div class="find-content">
          <div class="find-id" @click="showFindIdModal">아이디 찾기</div>
          <div class="find-pwd" @click="showFindPwdModal">비밀번호 찾기</div>
        </div>
      </div>
      <div class="login_card content_card no_show_content">
        <h1 class="title">아이 로그인</h1>
        <div>
          <div class="input-container">
            <input
              type="text"
              id="input_childId"
              required="required"
              v-model="childId"
              @keyup.enter="childLogin"
            />
            <label for="input_childId">아이디</label>
            <div class="bar"></div>
          </div>
          <div class="button-container">
            <div class="button-box">
              <div class="carrot" @click="childLogin"><span>로그인</span></div>
              <div class="leaves-1"></div>
              <div class="leaves-2"></div>
              <div class="leaves-3"></div>
            </div>
          </div>
          <!-- <div class="footer"><a href="#">Forgot your password?</a></div> -->
        </div>
      </div>
      <div v-if="checkModal1" class="modal-window modal1">
        <div class="infoModal">
          <div>
            <p class="modal_content">
              {{ checkModalMessage1 }}은(는) <br />
              필수 입력값입니다.
            </p>
          </div>
        </div>
      </div>
      <div v-if="checkModal2" class="modal-window modal2">
        <div class="infoModal">
          <div>
            <p class="modal_content">
              일치하는 회원 정보가 <br />
              없습니다!
            </p>
          </div>
        </div>
      </div>
      <div v-if="findIdCheckModal" class="find-modal">
        <div class="findId">
          <span class="find-modal-exit" @click="closeFindIdModal"
            ><i class="fas fa-times"></i
          ></span>
          <div class="input-container">
            <input
              type="text"
              id="find_input_name"
              required="required"
              v-model="nameForFind"
            />
            <label for="find_input_name">이름</label>
            <div class="bar"></div>
          </div>
          <div class="input-container">
            <input
              type="text"
              id="find_input_tel"
              required="required"
              v-model="telForFind"
            />
            <label for="find_input_tel">전화번호</label>
            <div class="bar"></div>
          </div>
          <div class="find-button-container" @click="findId">
            아이디 찾기
          </div>
        </div>
      </div>
      <div v-if="findPwdCheckModal" class="find-modal findPwd">
        <div class="findPwd">
          <span class="find-modal-exit" @click="closeFindPwdModal"
            ><i class="fas fa-times"></i
          ></span>
          <div class="input-container">
            <input
              type="text"
              id="find_input_name"
              required="required"
              v-model="nameForFind"
            />
            <label for="find_input_name">이름</label>
            <div class="bar"></div>
          </div>
          <div class="input-container">
            <input
              type="text"
              id="find_input_email"
              required="required"
              v-model="emailForFind"
            />
            <label for="find_input_email">이메일</label>
            <div class="bar"></div>
          </div>
          <div class="input-container">
            <input
              type="date"
              id="find_input_birth"
              required="required"
              v-model="birthForFind"
            />
            <label for="find_input_birth">생년월일</label>
            <div class="bar"></div>
          </div>
          <div class="find-button-container" @click="findPwd">
            비밀번호 찾기
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import "@/assets/css/User/Login.css";
import http from "@/api/http-common";

export default {
  data() {
    return {
      info: {
        email: "",
        password: "",
      },
      childId: "",
      checkModalMessage1: "",
      checkModal1: false,
      checkModal2: false,
      findIdCheckModal: false,
      findPwdCheckModal: false,
      nameForFind: "",
      telForFind: "",
      emailForFind: "",
      birthForFind: "",
    };
  },
  methods: {
    logIn() {
      setTimeout(() => {
        if (this.info.email === "") {
          this.checkModalMessage1 = "이메일";
          this.checkModal1 = true;

          setTimeout(() => {
            this.checkModal1 = false;
          }, 800);

          return;
        } else if (this.info.password === "") {
          this.checkModalMessage1 = "비밀번호";
          this.checkModal1 = true;

          setTimeout(() => {
            this.checkModal1 = false;
          }, 800);

          return;
        } else {
          var formData = new FormData();
          formData.append("parent_email", this.info.email);
          formData.append("parent_pwd", this.info.password);
          http
            .post("/login/loginByParent", formData)
            .then((res) => {
              localStorage.setItem("jwt", res.data.auth_token);
              localStorage.setItem("parent_id", res.data.parent_id);
              localStorage.setItem("loginType", 1);

              this.$emit("login");
              this.$router.push("/childchoice");
              location.reload();
            })
            .catch((err) => {
              console.error(err);
              this.checkModal2 = true;

              setTimeout(() => {
                this.checkModal2 = false;
              }, 800);
            });
        }
      }, 400);
    },
    Join() {
      setTimeout(() => {
        this.$router.push("/join");
      }, 400);
    },
    childLogin() {
      if (this.childId === "") {
        this.checkModalMessage1 = "아이디";
        this.checkModal1 = true;

        setTimeout(() => {
          this.checkModal1 = false;
        }, 800);

        return;
      } else {
        var formData = new FormData();
        formData.append("child_id", this.childId);

        http
          .post("/login/loginByChild", formData)
          .then((res) => {
            localStorage.setItem("child_jwt", res.data.auth_token);
            localStorage.setItem("child_id", res.data.child_id);
            localStorage.setItem("loginType", 2);

            this.getMyParent();
          })
          .catch((err) => {
            console.log(err);
            this.checkModal2 = true;

            setTimeout(() => {
              this.checkModal2 = false;
            }, 800);
          });
      }
    },
    toggleTab(e) {
      const active = document.querySelector(".active");
      const no_active = document.querySelector(".no_active");
      const show_content = document.querySelector(".show_content");
      const no_show_content = document.querySelector(".no_show_content");

      if (active == e.target) {
        return;
      } else {
        active.classList.remove("active");
        active.classList.add("no_active");

        no_active.classList.remove("no_active");
        no_active.classList.add("active");

        show_content.classList.remove("show_content");
        show_content.classList.add("no_show_content");

        no_show_content.classList.remove("no_show_content");
        no_show_content.classList.add("show_content");
      }
    },
    getMyParent() {
      http
        .get("/child/getMyChild", {
          params: {
            child_id: localStorage.getItem("child_id"),
          },
        })
        .then(
          (res) => {
            localStorage.setItem("parent_id", res.data.child_parentId);

            this.$router.push("/");
            location.reload();
          },
          (err) => {
            console.log(err);
          }
        );
    },
    showFindIdModal() {
      this.findIdCheckModal = true;
    },
    closeFindIdModal() {
      this.findIdCheckModal = false;
    },
    showFindPwdModal() {
      this.findPwdCheckModal = true;
    },
    closeFindPwdModal() {
      this.findPwdCheckModal = false;
    },
    findId() {
      var formData = new FormData();
      formData.append("parent_name", this.nameForFind);
      formData.append("parent_tel", this.telForFind);

      http.post("/login/findEmail", formData).then(
        (res) => {
          if (res.data != "") {
            alert("이메일 : " + res.data);

            this.findIdCheckModal = false;
            this.nameForFind = "";
            this.telForFind = "";
          } else {
            alert("일치하는 회원정보가 없습니다.");

            this.findIdCheckModal = false;
            this.nameForFind = "";
            this.telForFind = "";
          }
        },
        () => {
          alert("일치하는 회원정보가 없습니다.");

          this.findIdCheckModal = false;
          this.nameForFind = "";
          this.telForFind = "";
        }
      );
    },
    findPwd() {
      var formData = new FormData();
      formData.append("parent_name", this.nameForFind);
      formData.append("parent_email", this.emailForFind);
      formData.append("parent_birth", this.birthForFind);

      http.post("/login/findPassword", formData).then(
        (res) => {
          if (res.data != "") {
            alert("비밀번호 : " + res.data);

            this.findPwdCheckModal = false;
            this.nameForFind = "";
            this.emailForFind = "";
            this.birthForFind = "";
          } else {
            alert("일치하는 회원정보가 없습니다.");

            this.findPwdCheckModal = false;
            this.nameForFind = "";
            this.emailForFind = "";
            this.birthForFind = "";
          }
        },
        () => {
          alert("일치하는 회원정보가 없습니다.");

          this.findPwdCheckModal = false;
          this.nameForFind = "";
          this.emailForFind = "";
          this.birthForFind = "";
        }
      );
    },
  },
};
</script>

<template>
  <div class="Join">
    <div class="Join_container">
      <div class="card"></div>
      <div class="card">
        <h1 class="title">회원가입</h1>
        <div>
          <div class="input-container">
            <input
              type="text"
              id="input_email"
              required="required"
              v-model="credentials.email"
              @keyup.enter="join"
            />
            <label for="input_email">이메일</label>
            <div v-if="!checkEmail" id="input_email_check">
              <i class="far fa-check-circle"></i>
            </div>
            <button v-else class="input_email_check" @click="emailCheck">
              {{ checkEmail }}
            </button>
            <div class="bar"></div>
          </div>
          <div class="input-container">
            <input
              type="password"
              id="input_pwd"
              required="required"
              @keyup.enter="join"
              v-model="credentials.password"
            />
            <label for="input_pwd">비밀번호</label>
            <div class="bar"></div>
          </div>
          <div class="input-container">
            <input
              type="password"
              id="input_pwdconfirm"
              required="required"
              @keyup.enter="join"
              v-model="credentials.passwordConfirmation"
            />
            <label for="input_pwdconfirm">비밀번호 확인</label>
            <div class="bar"></div>
          </div>
          <div class="input-container">
            <input
              type="text"
              id="input_nickname"
              required="required"
              @keyup.enter="join"
              v-model="credentials.nickname"
            />
            <label for="input_nickname">별명</label>
            <div class="bar"></div>
          </div>
          <div class="input-container">
            <input
              type="text"
              id="input_tel"
              required="required"
              @keyup.enter="join"
              v-model="credentials.tel"
            />
            <label for="input_tel">전화번호</label>
            <div class="bar"></div>
          </div>
          <div class="input-container">
            <input
              class="date_font"
              type="date"
              id="input_birth"
              required="required"
              @keyup.enter="join"
              v-model="credentials.birth"
            />
            <label for="input_birth">생년월일</label>
            <div class="bar"></div>
          </div>
          <div class="button-container">
            <div class="button-box">
              <div class="carrot" @click="join"><span>회원가입</span></div>
              <div class="leaves-1"></div>
              <div class="leaves-2"></div>
              <div class="leaves-3"></div>
            </div>
          </div>
        </div>
      </div>
      <div v-if="checkModal1" class="modal-window modal1">
        <div>
          <div>
            <p class="modal_content">
              {{ checkModalMessage1 }}은(는) <br />
              필수 입력값입니다.
            </p>
          </div>
        </div>
      </div>
      <div v-if="checkModal2" class="modal-window modal2">
        <div>
          <div>
            <p class="modal_content">
              {{ checkModalMessage2 }}
            </p>
          </div>
        </div>
      </div>
      <div v-if="checkModal3" class="modal-window modal3">
        <div>
          <div>
            <p class="modal_content">
              {{ checkModalMessage3 }}
            </p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import "@/assets/css/User/Join.css";
import http from "@/api/http-common";

export default {
  data() {
    return {
      emailval: false,
      doublecheck: false,
      credentials: {
        email: "",
        password: "",
        passwordConfirmation: "",
        nickname: "",
        tel: "",
        birth: "",
      },
      checkEmail: "중복확인",
      passwordValidFlag: false,
      passwordCheckFlag: false,
      checkModalMessage1: "",
      checkModal1: false,
      checkModalMessage2: "",
      checkModal2: false,
      checkModalMessage3: "",
      checkModal3: false,
    };
  },
  methods: {
    join() {
      setTimeout(() => {
        
      
        this.passwordValid();
        this.passwordCheckValid();

        if (this.credentials.email == "") {
          this.checkModalMessage1 = "이메일";
          this.checkModal1 = true;

          setTimeout(() => {
            this.checkModal1 = false;
          }, 800);

          return;
        } else if (!this.doublecheck) {
          this.checkModalMessage2 = "이메일 중복체크는 필수입니다.";
          this.checkModal2 = true;

          setTimeout(() => {
            this.checkModal2 = false;
          }, 800);

          return;
        } else if (this.credentials.password == "") {
          this.checkModalMessage1 = "비밀번호";
          this.checkModal1 = true;

          setTimeout(() => {
            this.checkModal1 = false;
          }, 800);

          return;
        } else if (!this.passwordValidFlag) {
          this.credentials.password = "";

          this.checkModalMessage2 =
            "PW는 소문자 숫자 포함 6자 이상 12자 이하 입니다.";
          this.checkModal2 = true;

          setTimeout(() => {
            this.checkModal2 = false;
          }, 800);

          return;
        } else if (!this.passwordCheckFlag) {
          this.credentials.passwordConfirmation = "";

          this.checkModalMessage2 = "비밀번호가 일치하지 않습니다.";
          this.checkModal2 = true;

          setTimeout(() => {
            this.checkModal2 = false;
          }, 800);

          return;
        } else if (this.credentials.nickname == "") {
          this.checkModalMessage1 = "별명";
          this.checkModal1 = true;

          setTimeout(() => {
            this.checkModal1 = false;
          }, 800);

          return;
        } else if (this.credentials.tel == "") {
          this.checkModalMessage1 = "전화번호";
          this.checkModal1 = true;

          setTimeout(() => {
            this.checkModal1 = false;
          }, 800);

          return;
        } else if (this.credentials.tel.length !== 11) {
          this.checkModalMessage2 = "전화번호 입력값은 11자리 입니다.";
          this.checkModal2 = true;

          setTimeout(() => {
            this.checkModal2 = false;
          }, 800);

          return;
        } else if (this.credentials.birth == "") {
          this.checkModalMessage1 = "생년월일";
          this.checkModal1 = true;

          setTimeout(() => {
            this.checkModal1 = false;
          }, 800);

          return;
        } else {
          var formData = new FormData();
          formData.append("parent_email", this.credentials.email);
          formData.append("parent_pwd", this.credentials.password);
          formData.append("parent_name", this.credentials.nickname);
          formData.append("parent_tel", this.credentials.tel);
          formData.append("parent_birth", this.credentials.birth);
          http
            .post("/parent/joinParent", formData)
            .then(() => {
              this.checkModalMessage3 = "회원가입 완료!";
              this.checkModal3 = true;

              setTimeout(() => {
                this.checkModal3 = false;
                this.$router.push({ name: "Login" });
              }, 800);
            })
            .catch((err) => {
              console.log(err);
            });
        }
      }, 500);
    },
    emailCheck() {
      // 정규식 돌려서 하나 false
      if (
        /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i.test(
          this.credentials.email
        )
      ) {
        this.emailval = true;
      } else {
        this.emailval = false;
      }
      if (this.credentials.email === "") {
        this.checkModalMessage3 = "이메일을 입력해주세요.";
        this.checkModal3 = true;

        setTimeout(() => {
          this.checkModal3 = false;
        }, 800);

        return;
      } else if (!this.emailval) {
        this.checkModalMessage3 = "이메일을 형식을 맞춰주세요";
        this.checkModal3 = true;

        setTimeout(() => {
          this.checkModal3 = false;
        }, 800);

        return;
      } else {
        var formData = new FormData();
        formData.append("input_email", this.credentials.email);
        http
          .post("/parent/checkForDuplicateEmail", formData)
          .then((res) => {
            if (res.data.count === 0) {
              this.checkModalMessage3 = "중복된 이메일입니다.";
              this.checkModal3 = true;

              setTimeout(() => {
                this.checkModal3 = false;
              }, 800);

              return;
            } else {
              this.doublecheck = true;
              this.passwordValid();
              this.checkEmail = "";
              this.checkModalMessage3 = "사용가능한 이메일입니다.";
              this.checkModal3 = true;

              setTimeout(() => {
                this.checkModal3 = false;
              }, 800);

              return;
            }
          })
          .catch((err) => {
            console.log(err);
          });
      }
    },
    passwordValid() {
      if (/^[a-z0-9]{6,12}$/.test(this.credentials.password)) {
        // 내가 정규식을 통과해서 유효하다면
        this.passwordValidFlag = true;
        // 비밀번호 일치여부도 보러가자.
      } else {
        // 아니라면 패스
        this.passwordValidFlag = false;
      }
    },
    passwordCheckValid() {
      if (this.credentials.password === this.credentials.passwordConfirmation) {
        this.passwordCheckFlag = true;
      } else {
        this.passwordCheckFlag = false;
      }
    },
  },
};
</script>

<style scoped>
.join-container #join-box {
  width: 250px;
  padding: 40px;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background: yellowgreen;
  text-align: center;
}
.join-container #join-box h1 {
  color: white;
  text-transform: uppercase;
  font-weight: 500;
}
.join-container #join-box input[type="email"],
.join-container #join-box input[type="text"],
.join-container #join-box input[type="password"],
.join-container #join-box input[type="date"] {
  border: 0;
  background: none;
  display: block;
  margin: 20px auto;
  text-align: center;
  border: 2px solid black;
  padding: 10px 8px;
  width: 200px;
  outline: none;
  color: white;
  border-radius: 24px;
  transition: 0.25s;
}
.join-container #join-box input[type="email"]:focus,
.join-container #join-box input[type="text"]:focus,
.join-container #join-box input[type="password"]:focus,
.join-container #join-box input[type="date"]:focus {
  width: 240px;
  border-color: #2ecc71;
}

.join-container #join-box button {
  width: 140px;
  height: 45px;

  font-size: 11px;
  text-transform: uppercase;
  letter-spacing: 2.5px;
  font-weight: 500;
  color: #000;
  background-color: #fff;
  border: none;
  border-radius: 45px;
  box-shadow: 0px 8px 15px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease 0s;
  cursor: pointer;
  outline: none;
}
.join-container #join-box button:hover {
  background-color: #2ee59d;
  box-shadow: 0px 15px 20px rgba(46, 229, 157, 0.4);
  color: #fff;
  transform: translateY(-7px);
}
</style>

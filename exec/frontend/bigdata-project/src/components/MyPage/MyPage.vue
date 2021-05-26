<template>
  <!-- <div class="Mypage-container"> -->
  <div class="p-modify">
    <div class="modify_container">
      <div class="info_card"></div>
      <div class="info_card info_content_card info_show_content">
        <h1 class="info_title">부모 정보</h1>
        <div style="margin-bottom:10%;">
          <div class="info_input-container">
            <input
              type="text"
              id="input_email"
              required="required"
              v-model="parent.parent_email"
            />
            <label for="input_email">이메일</label>
            <div class="info_bar"></div>
          </div>

          <div class="info_input-container">
            <input
              type="text"
              id="input_name"
              required="required"
              v-model="parent.parent_name"
            />
            <label for="input_pwd">이름</label>
            <div class="info_bar"></div>
          </div>

          <div class="info_input-container">
            <input
              type="password"
              id="input_pwd"
              required="required"
              v-model="parent.parent_password"
            />
            <label for="input_pwd">비밀번호</label>
            <div class="info_bar"></div>
          </div>
          <div class="info_input-container">
            <input
              type="date"
              id="input_birth"
              required="required"
              v-model="parent.parent_birth"
            />
            <label for="input_pwd">생년월일</label>
            <div class="info_bar"></div>
          </div>
          <div class="info_input-container">
            <input
              type="text"
              id="input_tel"
              required="required"
              @keyup.enter="modifyParent"
              v-model="parent.parent_tel"
            />
            <label for="input_pwd">번호</label>
            <div class="info_bar"></div>
          </div>
          <div class="info_button-container">
            <div class="button-box">
              <div class="carrot info_modifyInfo" @click="modifyParent()">
                <span>부모 정보 수정</span>
              </div>
              <div class="leaves-1"></div>
              <div class="leaves-2"></div>
              <div class="leaves-3"></div>
            </div>
          </div>
          <div v-if='modal' class="modal-window">
            <div>
              <div><p class="modal_content">수정 완료!</p></div>
            </div>
          </div>
        </div>
        <button class="leaveParent" @click="deleteUserModal">회원 탈퇴</button>
        <div v-if="deleteModal" class="delete-user">
          <div class="delete-user-box">
            <div class="delete-content">정말로 삭제하시 겠습니까?</div>

            <div class="delete-modal-button">
              <div class="delete-button-container">
                <div class="button-box">
                  <div class="carrot" @click="deleteUser">예</div>
                  <div class="leaves-1"></div>
                  <div class="leaves-2"></div>
                  <div class="leaves-3"></div>
                </div>
              </div>
              <div class="delete-button-container">
                <div class="button-box">
                  <div class="carrot" @click="deleteUserModal">아니오</div>
                  <div class="leaves-1"></div>
                  <div class="leaves-2"></div>
                  <div class="leaves-3"></div>
                </div>
              </div>
            </div>
          </div>
        </div>
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
      parent: {
        parent_email: "",
        parent_pwd: "",
        parent_name: "",
        parent_tel: "",
        parent_birth: "",
      },
      childId: "",
      modal: false,
      deleteModal:false,
    };
  },
  mounted() {
    // this.getParentInfo();
  },
  created() {
    this.getParentInfo();
  },
  updated() {
    this.checkModal();
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
    checkModal() {
      return this.modal;
    },
    modifyParent() {
      setTimeout(() => {
        const formData = new FormData();
        formData.append("parent_birth", this.parent.parent_birth);
        formData.append("parent_id", localStorage.getItem("parent_id"));
        formData.append("parent_name", this.parent.parent_name);
        formData.append("parent_pwd", this.parent.parent_pwd);
        formData.append("parent_tel", this.parent.parent_tel);
        http.post("/parent/modifyParentInfo", formData).then(
          () => {
            this.modal = true;
            window.setTimeout(function() {location.reload(true);}, 500);
          },
          (error) => {
            console.log(error);
          }
        );
      }, 500);
    },
    movepath(event) {
      if (this.$route.name === event) {
        window.location.reload();
      } else {
        this.$router.push({ name: event });
      }
    },
    deleteUserModal(){
      setTimeout(() => {
        this.deleteModal = !this.deleteModal
      }, 500);
    },
    deleteUser() {
      var formData = new FormData();
      formData.append("parent_id", localStorage.getItem('parent_id'));
      http.post('/parent/leaveParent/', formData)
      .then(()=>{
        localStorage.clear()
        this.$router.push({name:"Login"})
      }).error((err)=>{
        console.error(err)
      })
    },
  },
};
</script>

<style>
@charset "UTF-8";

.p-modify {
  color: #666666;
  font-size: 14px;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  height: 100%;
  padding-bottom: 15vh;
}

/* Container */
.p-modify .modify_container {
  position: relative;
  width: 100%;
  max-width: 95vw;
  top: 5vh;
  /* backdrop-filter: saturate(180%) blur(3px); */
}

.modify_container.active .info_card:first-child {
  background: #f2f2f2;
  margin: 0 15px;
}

.modify_container.active .info_card:nth-child(2) {
  background: #fafafa;
  margin: 0 10px;
}

.modify_container.active .info_card.alt {
  top: 20px;
  right: 0;
  width: 100%;
  min-width: 100%;
  height: auto;
  border-radius: 5px;
  padding: 60px 0 40px;
  overflow: hidden;
}

.modify_container.active .info_card.alt .info_title,
.modify_container.active .info_card.alt .info_input-container {
  left: 0;
  opacity: 1;
  visibility: visible;
  transition: 0.3s ease;
}
.modify_container.active .info_card.alt .info_title {
  transition-delay: 0.3s;
}
.modify_container.active .info_card.alt .info_input-container {
  transition-delay: 0.4s;
}
.modify_container.active .info_card.alt .info_input-container:nth-child(2) {
  transition-delay: 0.5s;
}
.modify_container.active .info_card.alt .info_input-container:nth-child(3) {
  transition-delay: 0.6s;
}

/* Card */
.info_card {
  position: relative;
  background: #ffffff;
  border-radius: 5px;
  padding: 30px 0 40px 0;
  box-sizing: border-box;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.12), 0 1px 2px rgba(0, 0, 0, 0.24);
  transition: 0.3s ease;
  /* Title */
  /* Inputs */
  /* Button */
  /* Footer */
  /* Alt Card */
}
.info_card:first-child {
  background: #fafafa;
  height: 10px;
  border-radius: 5px 5px 0 0;
  margin: 0 10px;
  padding: 0;
}
.info_card .info_title {
  position: relative;
  z-index: 1;
  border-left: 5px solid #40aa57;
  margin: 0 0 35px;
  padding: 10px 0 3px 50px;
  color: #40aa57;
  font-size: 32px;
  font-weight: 600;
  text-transform: uppercase;
}
.info_card .info_input-container {
  position: relative;
  margin: 0 60px 40px;
}
.info_card .info_input-container input {
  outline: none;
  /* z-index: 1; */
  position: relative;
  background: none;
  width: 100%;
  height: 50px;
  border: 0;
  color: #212121;
  font-size: 20px;
  font-weight: 400;
}
.info_card .info_input-container input:focus ~ label {
  color: #9d9d9d;
  transform: translate(-12%, -50%) scale(0.75);
}
.info_card .info_input-container input:focus ~ .info_bar:before,
.info_card .info_input-container input:focus ~ .info_bar:after {
  width: 50%;
}
.info_card .info_input-container input:valid ~ label {
  color: #9d9d9d;
  transform: translate(-12%, -50%) scale(0.75);
}
.info_card .info_input-container label {
  position: absolute;
  top: 0;
  width: 100%;
  left: 0;
  color: #757575;
  font-size: 20px;
  font-weight: 300;
  line-height: 60px;
  transition: 0.2s ease;
}
.info_card .info_input-container .info_bar {
  position: absolute;
  left: 0;
  bottom: 0;
  background: #757575;
  width: 100%;
  height: 1px;
}
.info_card .info_input-container .info_bar:before,
.info_card .info_input-container .info_bar:after {
  content: "";
  position: absolute;
  background: #40aa57;
  width: 0;
  height: 2px;
  transition: 0.2s ease;
}
.info_card .info_input-container .info_bar:before {
  left: 50%;
}
.info_card .info_input-container .info_bar:after {
  right: 50%;
}
.info_card .footer {
  margin: 40px 0 0;
  color: #d3d3d3;
  font-size: 24px;
  font-weight: 300;
  text-align: center;
}
.info_card .footer a {
  color: inherit;
  text-decoration: none;
  transition: 0.3s ease;
}
.info_card .footer a:hover {
  color: #bababa;
}
.info_card.alt {
  position: absolute;
  top: 40px;
  right: -70px;
  z-index: 10;
  width: 140px;
  height: 140px;
  background: none;
  border-radius: 100%;
  box-shadow: none;
  padding: 0;
  transition: 0.3s ease;
  /* Toggle */
  /* Title */
  /* Input */
  /* Button */
}
.info_card.alt .toggle {
  position: relative;
  background: #40aa57;
  width: 140px;
  height: 140px;
  border-radius: 100%;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.12), 0 1px 2px rgba(0, 0, 0, 0.24);
  color: #ffffff;
  font-size: 58px;
  line-height: 140px;
  text-align: center;
  cursor: pointer;
}
.info_card.alt .toggle:before {
  content: "";
  display: inline-block;
  font: normal normal normal 14px/1 FontAwesome;
  font-size: inherit;
  text-rendering: auto;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  transform: translate(0, 0);
}
.info_card.alt .info_title,
.info_card.alt .info_input-container {
  left: 100px;
  opacity: 0;
  visibility: hidden;
}
.info_card.alt .info_title {
  position: relative;
  border-color: #ffffff;
  color: #ffffff;
}
.info_card.alt .info_title .close {
  cursor: pointer;
  position: absolute;
  top: 0;
  right: 60px;
  display: inline;
  color: #ffffff;
  font-size: 58px;
  font-weight: 400;
}
.info_card.alt .info_title .close:before {
  content: "×";
}
.info_card.alt .info_input-container input {
  color: #ffffff;
}
.info_card.alt .info_input-container input:focus ~ label {
  color: #ffffff;
}
.info_card.alt .info_input-container input:focus ~ .info_bar:before,
.info_card.alt .info_input-container input:focus ~ .info_bar:after {
  background: #ffffff;
}
.info_card.alt .info_input-container input:valid ~ label {
  color: #ffffff;
}
.info_card.alt .info_input-container label {
  color: rgba(255, 255, 255, 0.8);
}
.info_card.alt .info_input-container .info_bar {
  background: rgba(255, 255, 255, 0.8);
}

/* Keyframes */
@-webkit-keyframes buttonFadeInUp {
  0% {
    bottom: 30px;
    opacity: 0;
  }
}
@keyframes buttonFadeInUp {
  0% {
    bottom: 30px;
    opacity: 0;
  }
}

/* 현우버튼 */
.p-modify .info_button-container .button-box{
  margin: auto;
  display: flex;
  justify-content: center;
  align-items: center;
  text-align: center;
  cursor: pointer;
  max-width: 160px;
  max-height: 43px;
  width: 40%;
  height: 6vh;
  transition: 0.25s cubic-bezier(0.25, 0, 0, 1);
}

.p-modify .info_button-container .button-box:active {
  transform: translateY(5%);
}


.p-modify .info_button-container .button-box .carrot {
  position: relative;
  display: inline-block;
  width: 100%;
  height: 100%;
  z-index: 3;
  padding-left: 0.5em;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 20px;
  font-weight: 600;
  text-align: center;
  border-radius: 10px;
  background: #f3951b;
  color: white;
  border-radius: 60% 20px 20px 60%;
}
.p-modify .info_button-container .leaves-1,
.p-modify .info_button-container .leaves-2,
.p-modify .info_button-container .leaves-3 {
  position: absolute;
  width: 5px;
  z-index: 0;
  height: 5px;
  border-right: 40px solid #40aa57;
  border-bottom: 10px solid transparent;
  border-radius: 50%;
  transition: 0.25s cubic-bezier(0.25, 0, 0, 1);
  right: -30px;
}
.p-modify .info_button-container .leaves-1 {
  transform: rotate(-30deg);
  top: 5px;
}
.p-modify .info_button-container .leaves-2 {
  top: 15px;
}
.p-modify .info_button-container .leaves-3 {
  transform: rotate(30deg);
  top: 25px;
}


/* === */

.modal-window {
  backdrop-filter: saturate(100%) blur(3px);
  position: fixed;
  background-color: rgba(255, 255, 255, 0.25);
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  z-index: 999;
  border-radius: 1rem;
  visibility: visible;
  pointer-events: auto;
  opacity: 1;
  transition: all 0.3s;
}

.modal-window div {
  width: 70vw;
  height: 10vh;
    /* position: absolute; */
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    padding: 2em;
    background: white;
    /* box-shadow: 10em gray; */
    box-shadow: 20px 20px 20px grey;
    border-radius: 25px;
    /* border: 2px solid #f3951b;
    background-color: #f3951b; */
}

.modal_content {
  font-size: 25px;
  text-align: center;
  color: #40aa57;
  /* margin-top: 3%; */
}


.delete-user{
  width: 100vw;
  height: 100vh;
  position: fixed;
  top:0;
  display: flex;
  justify-content: center;
  align-items: center;
  left: 0;
  z-index: 999;
  backdrop-filter: saturate(100%) blur(3px);
}
.delete-user .delete-user-box{
  width: 80%;
  height: 20%;
  display: flex;
  flex-direction: column;
  justify-content: space-evenly;
  align-items: center;
  background: white;
  border-radius: 20px;
  font-size: 1.5rem;
  box-shadow: 10px 10px 20px 5px gray;
  top:0;
  left: 0;
  z-index: 999;
}



/* 삭제버튼 */
.delete-modal-button {
  width: 100%;
  display: flex;
}
.delete-modal-button .delete-button-container {
  display: flex;
  width: 100%;
  padding-left: 5%;
}
@media( min-width : 500px) {
  .delete-modal-button .delete-button-container .button-box{
    margin: auto;
    padding-left: 0;
  }
  .delete-modal-button .delete-button-container {
  padding-left: 0;
  }
}
.delete-modal-button .delete-button-container .button-box{
  display: flex;
  cursor: pointer;
  max-width: 160px;
  max-height: 43px;
  width: 70%;
  height: 6vh;
  transition: 0.25s cubic-bezier(0.25, 0, 0, 1);
}
.delete-modal-button .delete-button-container .button-box:active {
  transform: translateY(5%);
}
.delete-modal-button .delete-button-container .button-box .carrot {
  position: relative;
  display: inline-block;
  width: 100%;
  height: 100%;
  z-index: 3;
  padding-left: 0.5em;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 20px;
  font-weight: 600;
  text-align: center;
  border-radius: 10px;
  background: #f3951b;
  color: white;
  border-radius: 60% 20px 20px 60%;
}
.delete-modal-button .delete-button-container .button-box .leaves-1,
.delete-modal-button .delete-button-container .button-box .leaves-2,
.delete-modal-button .delete-button-container .button-box .leaves-3 {
  position: absolute;
  width: 5px;
  z-index: 0;
  height: 5px;
  border-right: 40px solid #40aa57;
  border-bottom: 10px solid transparent;
  border-radius: 50%;
  transition: 0.25s cubic-bezier(0.25, 0, 0, 1);
  right: -30px;
}
.delete-modal-button .delete-button-container .button-box .leaves-1 {
  transform: rotate(-30deg);
  top: 5px;
}
.delete-modal-button .delete-button-container .button-box .leaves-2 {
  top: 15px;
}
.delete-modal-button .delete-button-container .button-box .leaves-3 {
  transform: rotate(30deg);
  top: 25px;
}



.leaveParent{
  transition: 0.25s cubic-bezier(0.25, 0, 0, 1);
}
.leaveParent:active{
  transform: translateY(5%);
}
</style>
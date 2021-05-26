<template>
  <div class="c-modify">
    <div class="modify_container">
      <div class="c-info_card"></div>
      <div class="c-info_card c-info_content_card c-info_show_content">
        <h1 class="c-info_title">아이 정보</h1>
        <div>
          <!-- ========================= -->
          <div class="avatar-upload">
            <div class="avatar-edit">
              <input
                type="file"
                id="imageUpload"
                accept=".png, .jpg, .jpeg"
                @change="onImages"
              />
              <label class="upload-label" for="imageUpload">
                <div class="leaves-1"></div>
                <div class="leaves-2"></div>
                <div class="leaves-3"></div>
                <div class="avatar-preview">
                  <div id="imagePreview"></div>
                </div>
                <div class="carrot"></div>
              </label>
            </div>
            <!-- <div class="avatar-preview">
              <div id="imagePreview"></div>
            </div> -->
          </div>
          <!-- =========================== -->
          <div class="c-info_input-container">
            <input
              type="text"
              required="required"
              v-model="childInfo.child_name"
            />
            <label for="input_name">이름</label>
            <div class="c-info_bar"></div>
          </div>

          <div class="c-info_input-container">
            <div class="half">
              <div class="half-l">
                <select
                  name="child_area"
                  id="child_area"
                  v-model="childInfo.child_area"
                >
                  <option value="">선택</option>
                  <option value="B10">서울특별시</option>
                  <option value="C10">부산광역시</option>
                  <option value="D10">대구광역시</option>
                  <option value="E10">인천광역시</option>
                  <option value="F10">광주광역시</option>
                  <option value="G10">대전광역시</option>
                  <option value="H10">울산광역시</option>
                  <option value="I10">세종특별자치시</option>
                  <option value="J10">경기도</option>
                  <option value="K10">강원도</option>
                  <option value="N10">충청남도</option>
                  <option value="M10">충청북도</option>
                  <option value="Q10">전라남도</option>
                  <option value="P10">전라북도</option>
                  <option value="S10">경상남도</option>
                  <option value="R10">경상북도</option>
                  <option value="T10">제주특별자치도</option>
                </select>
                <!-- <input
                  type="text"
                  required="required"
                  v-model="childInfo.child_area"
                /> -->
                <label for="child_area">지역</label>
                <div class="c-info_bar"></div>
              </div>
              <div class="half-r">
                <input
                  type="text"
                  required="required"
                  v-model="childInfo.child_schoolName"
                />
                <label for="input_pwd">학교명</label>
                <div class="c-info_bar"></div>
              </div>
            </div>
          </div>

          <div class="c-info_input-container">
            <input
              type="date"
              required="required"
              v-model="childInfo.child_birth"
            />
            <label for="input_pwd">생년월일</label>
            <div class="c-info_bar"></div>
          </div>
          <div class="c-info_input-container">
            <div class="half">
              <div class="half-l">
                <input
                  type="text"
                  required="required"
                  v-model="childInfo.child_height"
                />
                <label for="input_height">키(cm)</label>
                <div class="c-info_bar"></div>
              </div>
              <div class="half-r">
                <input
                  type="text"
                  required="required"
                  @keyup.enter="modifyChild"
                  v-model="childInfo.child_weight"
                />
                <label for="input_weight">몸무게(kg)</label>
                <div class="c-info_bar"></div>
              </div>
            </div>
          </div>
          <div class="c-info_button-container">
            <div class="button-box">
              <button class="carrot" @click="modifyChild()">
                <span>아이 정보 수정</span>
              </button>
              <div class="leaves-1"></div>
              <div class="leaves-2"></div>
              <div class="leaves-3"></div>
            </div>
          </div>
          <div v-if="modal" class="modal-window">
            <div class="child-modal">
              <div><p class="modal_content">수정 완료!</p></div>
            </div>
          </div>
          <div class="leaveChild" @click="deleteChildModal">아이정보 삭제</div>
          <div v-if="deleteModal" class="delete-child">
            <div class="delete-child-box">
              <div class="delete-content">정말로 삭제하시 겠습니까?</div>

              <div class="delete-child-button">
                <div class="delete-button-container">
                  <div class="button-box">
                    <div class="carrot" @click="deleteChild">예</div>
                    <div class="leaves-1"></div>
                    <div class="leaves-2"></div>
                    <div class="leaves-3"></div>
                  </div>
                </div>
                <div class="delete-button-container">
                  <div class="button-box">
                    <div class="carrot" @click="deleteChildModal">아니오</div>
                    <div class="leaves-1"></div>
                    <div class="leaves-2"></div>
                    <div class="leaves-3"></div>
                  </div>
                </div>
              </div>
              <!-- <div class="delete-btn-box">
                <div class="execute-delete">
                  <div class="leaves-1"></div>
                  <div class="leaves-2"></div>
                  <div class="leaves-3"></div>
                  <div @click="deleteChild" class="delete-btn">예</div>
                </div>
                <div class="cancle-delete">
                  <div class="leaves-1"></div>
                  <div class="leaves-2"></div>
                  <div class="leaves-3"></div>
                  <div @click="deleteChildModal" class="delete-content">아니오</div>
                </div>
              </div> -->


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
      childInfo: {
        child_parentId: "",
        child_area: "",
        child_schoolName: "",
        child_name: "",
        child_sex: "",
        child_birth: "",
        child_height: "",
        child_weight: "",
        child_profile: "",
        child_BMI: "",
      },
      img_url: "",
      change_profile: false,
      modal: false,
      deleteModal : false,
    };
  },
  mounted() {
    // this.getChildInfo();
  },
  created() {
    this.getChildInfo();
  },
  updated() {
    this.checkModal();
  },
  methods: {
    deleteChild(){
      setTimeout(() => {
        var formData = new FormData();
        formData.append("child_id", localStorage.getItem('child_id'));
        http.post('/child/deleteChild/', formData)
        .then(()=>{
          localStorage.removeItem('child_id')
          this.$router.push({name:"ChildChoice"})
        }).error((err)=>{
          console.error(err)
        })
      }, 500);
    },
    deleteChildModal() {
      setTimeout(() => {
        this.deleteModal = !this.deleteModal
      }, 500);
    },
    checkModal() {
      return this.modal;
    },
    getChildInfo() {
      http
        .get("/child/getMyChild", {
          params: {
            child_id: localStorage.getItem("child_id"),
          },
        })
        .then((response) => {
          this.childInfo.child_area = response.data.child_area;
          this.childInfo.child_schoolName = response.data.child_schoolName;
          this.childInfo.child_name = response.data.child_name;
          this.childInfo.child_sex = response.data.child_sex;
          this.childInfo.child_birth = response.data.child_birth;
          this.childInfo.child_height = response.data.child_height;
          this.childInfo.child_weight = response.data.child_weight;
          this.childInfo.child_profile = response.data.child_profile;

          let preview = document.querySelector("#imagePreview");
          this.img_url =
            "data:image/jpeg;base64," + response.data.child_profile;
          preview.style.backgroundImage = "url('" + this.img_url + "')";
        })
        .catch((err) => {
          console.log(err);
        });
    },
    onImages(e) {
      const profile_file = e.target.files[0];
      this.childInfo.child_profile = profile_file;
      const profile_preview = URL.createObjectURL(profile_file);
      this.change_profile = true;

      let preview = document.querySelector("#imagePreview");
      preview.style.backgroundImage = "url(" + `${profile_preview}` + ")";
    },
    modifyChild() {
      setTimeout(() => {
        this.modal = true;
        if (!this.change_profile) {
          const byteCharacters = atob(this.img_url.slice(23));
          const byteNumbers = new Array(byteCharacters.length);
          for (let i = 0; i < byteCharacters.length; i++) {
            byteNumbers[i] = byteCharacters.charCodeAt(i);
          }
          const byteArray = new Uint8Array(byteNumbers);
          const blob = new Blob([byteArray], { type: "image/jpeg" });
          const file = new File([blob], "profile");
          this.childInfo.child_profile = file;
        }

        const formData = new FormData();
        formData.append("child_id", localStorage.getItem("child_id"));
        formData.append("child_area", this.childInfo.child_area);
        formData.append("child_schoolName", this.childInfo.child_schoolName);
        formData.append("child_name", this.childInfo.child_name);
        formData.append("child_sex", this.childInfo.child_sex);
        formData.append("child_birth", this.childInfo.child_birth);
        formData.append("child_height", this.childInfo.child_height);
        formData.append("child_weight", this.childInfo.child_weight);
        formData.append("child_profile", this.childInfo.child_profile);

        http.post(`/child/updateChild`, formData).then(
          () => {
            location.reload(true);
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
  },
};
</script>

<style>
.c-modify {
  padding-bottom: 15vh;
}
.modify_container .avatar-upload {
  position: relative;
  max-width: 205px;
  margin: 0 auto;
  margin-bottom: 5%;
  /* z-index: 10;
  background-color: white; */
}
.modify_container .avatar-upload .avatar-edit {
  width: 192px;
  height: 192px;
  position: relative;
}
.modify_container .avatar-upload .avatar-edit input {
  display: none;
}
.modify_container .avatar-upload .avatar-edit .upload-label {
  width: 192px;
  height: 192px;
  position: relative;
  display: block;
}
.modify_container .avatar-upload .avatar-edit .upload-label .avatar-preview {
  position: relative;
  z-index: 5;
  background: white;
  width: 100%;
  height: 100%;
  border-radius: 100%;
  border: 6px solid #f3951b;
  display: block;
}
.modify_container .avatar-upload .avatar-edit .leaves-1,
.modify_container .avatar-upload .avatar-edit .leaves-2,
.modify_container .avatar-upload .avatar-edit .leaves-3 {
  cursor: pointer;
  position: absolute;
  width: 10px;
  height: 10px;
  border-right: 60px solid #40aa57;
  border-bottom: 20px solid transparent;
  border-radius: 50%;
  -o-transform: scale(1.2);
  transition: all 0.2s ease-in-out;
}
.modify_container .avatar-upload .avatar-edit .leaves-1 {
  top: -2%;
  right: -4%;
  transform: rotate(-70deg);
}
.modify_container .avatar-upload .avatar-edit .leaves-2 {
  top: 5%;
  right: -13%;
  transform: rotate(-40deg);
}
.modify_container .avatar-upload .avatar-edit .leaves-3 {
  top: 15%;
  right: -16%;
  transform: rotate(-10deg);
}
.modify_container .avatar-upload .avatar-edit .carrot {
  cursor: pointer;
  position: absolute;
  top: -4%;
  left: -6%;
  width: 210px;
  height: 210px;
  background: #f3951b;
  -o-transform: scale(1.2);
  border-radius: 100% 100% 100% 20%;
  transform: rotate(10deg);
  transition: all 0.2s ease-in-out;
}
.modify_container .avatar-upload .avatar-edit label:after {
  content: "\f040";
  font-family: "FontAwesome";
  color: #757575;
  position: absolute;
  top: 10px;
  left: 0;
  right: 0;
  text-align: center;
  margin: auto;
}
/* .modify_container .avatar-upload .avatar-preview {
  width: 192px;
  height: 192px;
  position: relative;
  border-radius: 100%;
  border: 6px solid #f3951b;
} */
.modify_container .avatar-upload .avatar-preview #imagePreview {
  width: 100%;
  height: 100%;
  border-radius: 100%;
  background-size: cover;
  background-repeat: no-repeat;
  background-position: center;
  z-index: 10;
  
}

/* ========== */

.modify {
  color: #666666;
  font-size: 14px;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  height: 100%;
}

/* Container */
.modify_container {
  position: relative;
  width: 100%;
  max-width: 95vw;
  top: 3vh;
  margin: 0 auto;
}

.modify_container.active .c-info_card:first-child {
  background: #f2f2f2;
  margin: 0 15px;
}

.modify_container.active .c-info_card:nth-child(2) {
  background: #fafafa;
  margin: 0 10px;
}

.modify_container.active .c-info_card.alt {
  top: 20px;
  right: 0;
  width: 100%;
  min-width: 100%;
  height: auto;
  border-radius: 5px;
  padding: 60px 0 40px;
  overflow: hidden;
}

.modify_container.active .c-info_card.alt .c-info_title,
.modify_container.active .c-info_card.alt .c-info_input-container {
  left: 0;
  opacity: 1;
  visibility: visible;
  transition: 0.3s ease;
}



/* 현우 수정버튼 */
.c-modify .c-info_button-container .button-box{
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
.c-modify .c-info_button-container .button-box:active {
  transform: translateY(5%);
}
.c-modify .c-info_button-container .button-box .carrot {
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
.c-modify .c-info_button-container .leaves-1,
.c-modify .c-info_button-container .leaves-2,
.c-modify .c-info_button-container .leaves-3 {
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
.c-modify .c-info_button-container .leaves-1 {
  transform: rotate(-30deg);
  top: 5px;
}
.c-modify .c-info_button-container .leaves-2 {
  top: 15px;
}
.c-modify .c-info_button-container .leaves-3 {
  transform: rotate(30deg);
  top: 25px;
}



.modify_container.active .c-info_card.alt .c-info_title {
  transition-delay: 0.3s;
}
.modify_container.active .c-info_card.alt .c-info_input-container {
  transition-delay: 0.4s;
}
.modify_container.active .c-info_card.alt .c-info_input-container:nth-child(2) {
  transition-delay: 0.5s;
}
.modify_container.active .c-info_card.alt .c-info_input-container:nth-child(3) {
  transition-delay: 0.6s;
}

/* Card */
.c-info_card {
  position: relative;
  background: #ffffff;
  border-radius: 5px;
  padding: 30px 0 20px 0;
  box-sizing: border-box;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.12), 0 1px 2px rgba(0, 0, 0, 0.24);
  transition: 0.3s ease;
  /* Title */
  /* Inputs */
  /* Button */
  /* Footer */
  /* Alt Card */
}
.c-info_card:first-child {
  background: #fafafa;
  height: 10px;
  border-radius: 5px 5px 0 0;
  margin: 0 10px;
  padding: 0;
}
.c-info_card .c-info_title {
  position: relative;
  z-index: 1;
  border-left: 5px solid #40aa57;
  margin: 0 0 10px;
  padding: 10px 0 3px 50px;
  color: #40aa57;
  font-size: 32px;
  font-weight: 600;
  text-transform: uppercase;
}
.c-info_card .c-info_input-container {
  position: relative;
  margin: 0 60px 30px;
}
.half {
  display: flex;
}
.half .half-l {
  width: 40%;
  margin-right: 20%;
}
.half .half-r {
  width: 40%;
}

.c-info_card .c-info_input-container input {
  outline: none;
  /* z-index: 1; */
  position: relative;
  background: none;
  width: 100%;
  height: 50px;
  border: 0;
  color: #212121;
  font-size: 18px;
  font-weight: 400;
}
.c-info_card .c-info_input-container select {
  outline: none;
  /* z-index: 1; */
  position: relative;
  background: none;
  width: 100%;
  height: 50px;
  border: 0;
  color: #212121;
  font-size: 18px;
  font-weight: 400;
}
.c-info_card .c-info_input-container input:focus ~ label {
  color: #9d9d9d;
  transform: translate(-12%, -50%) scale(0.75);
}
.c-info_card .c-info_input-container input:focus ~ .c-info_bar:before,
.c-info_card .c-info_input-container input:focus ~ .c-info_bar:after {
  width: 50%;
}
.c-info_card .c-info_input-container input:valid ~ label {
  color: #9d9d9d;
  transform: translate(-12%, -50%) scale(0.75);
}
.c-info_card .c-info_input-container select:focus ~ label {
  color: #9d9d9d;
  transform: translate(-12%, -50%) scale(0.75);
}
.c-info_card .c-info_input-container select:focus ~ .c-info_bar:before,
.c-info_card .c-info_input-container select:focus ~ .c-info_bar:after {
  width: 50%;
}
.c-info_card .c-info_input-container select:valid ~ label {
  color: #9d9d9d;
  transform: translate(-12%, -50%) scale(0.75);
}

.c-info_card .c-info_input-container label {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  color: #757575;
  font-size: 18px;
  font-weight: 300;
  line-height: 60px;
  transition: 0.2s ease;
}
.c-info_card .c-info_input-container .c-info_bar {
  position: absolute;
  left: 0;
  bottom: 0;
  background: #757575;
  width: 100%;
  height: 1px;
}
.c-info_card .c-info_input-container .c-info_bar:before,
.c-info_card .c-info_input-container .c-info_bar:after {
  content: "";
  position: absolute;
  background: #40aa57;
  width: 0;
  height: 2px;
  transition: 0.2s ease;
}
.c-info_card .c-info_input-container .c-info_bar:before {
  left: 50%;
}
.c-info_card .c-info_input-container .c-info_bar:after {
  right: 50%;
}
.c-info_card .footer {
  margin: 40px 0 0;
  color: #d3d3d3;
  font-size: 24px;
  font-weight: 300;
  text-align: center;
}
.c-info_card .footer a {
  color: inherit;
  text-decoration: none;
  transition: 0.3s ease;
}
.c-info_card .footer a:hover {
  color: #bababa;
}
.c-info_card.alt {
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
.c-info_card.alt .toggle {
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
.c-info_card.alt .toggle:before {
  content: "";
  display: inline-block;
  font: normal normal normal 14px/1 FontAwesome;
  font-size: inherit;
  text-rendering: auto;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  transform: translate(0, 0);
}
.c-info_card.alt .c-info_title,
.c-info_card.alt .c-info_input-container {
  left: 100px;
  opacity: 0;
  visibility: hidden;
}
.c-info_card.alt .c-info_title {
  position: relative;
  border-color: #ffffff;
  color: #ffffff;
}
.c-info_card.alt .c-info_title .close {
  cursor: pointer;
  position: absolute;
  top: 0;
  right: 60px;
  display: inline;
  color: #ffffff;
  font-size: 58px;
  font-weight: 400;
}
.c-info_card.alt .c-info_title .close:before {
  content: "×";
}
.c-info_card.alt .c-info_input-container input {
  color: #ffffff;
}
.c-info_card.alt .c-info_input-container input:focus ~ label {
  color: #ffffff;
}
.c-info_card.alt .c-info_input-container input:focus ~ .c-info_bar:before,
.c-info_card.alt .c-info_input-container input:focus ~ .c-info_bar:after {
  background: #ffffff;
}
.c-info_card.alt .c-info_input-container input:valid ~ label {
  color: #ffffff;
}
.c-info_card.alt .c-info_input-container label {
  color: rgba(255, 255, 255, 0.8);
}
.c-info_card.alt .c-info_input-container .c-info_bar {
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
.modal-window .child-modal {
  padding: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  background: white;
  box-shadow: 20px 20px 20px grey;
  border-radius: 25px;
}

.modal_content {
  font-size: 25px;
  text-align: center;
  color: #40aa57;
  /* margin-top: 3%; */
}
.modIcon {
  width: 28px;
  height: 28px;
  z-index: 11;
  top: 1px;
  left: 41px;
  right: 0;
  transform: rotate(44deg);
}



/* 현우 */
.delete-child{
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
.delete-child .delete-child-box{
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


.delete-child-button {
  width: 100%;
  display: flex;
}
.delete-child-button .delete-button-container {
  display: flex;
  width: 100%;
  padding-left: 5%;
}
@media( min-width : 500px) {
  .delete-child-button .delete-button-container .button-box{
    margin: auto;
    padding-left: 0;
  }
  .delete-child-button .delete-button-container {
  padding-left: 0;
  }
}
.delete-child-button .delete-button-container .button-box{
  display: flex;
  cursor: pointer;
  max-width: 160px;
  max-height: 43px;
  width: 70%;
  height: 6vh;
  transition: 0.25s cubic-bezier(0.25, 0, 0, 1);
}
.delete-child-button .delete-button-container .button-box:active {
  transform: translateY(5%);
}
.delete-child-button .delete-button-container .button-box .carrot {
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
.delete-child-button .delete-button-container .button-box .leaves-1,
.delete-child-button .delete-button-container .button-box .leaves-2,
.delete-child-button .delete-button-container .button-box .leaves-3 {
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
.delete-child-button .delete-button-container .button-box .leaves-1 {
  transform: rotate(-30deg);
  top: 5px;
}
.delete-child-button .delete-button-container .button-box .leaves-2 {
  top: 15px;
}
.delete-child-button .delete-button-container .button-box .leaves-3 {
  transform: rotate(30deg);
  top: 25px;
}



.leaveChild{
  color: #757575;
  margin: 5% 0 0 5%;
  transition: 0.25s cubic-bezier(0.25, 0, 0, 1);
}
.leaveChild:active{
  transform: translateY(5%);
}
</style>

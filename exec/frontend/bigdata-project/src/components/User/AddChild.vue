<template>
  <div class="addchild">
    <div class="addchild-container">
      <div class="addchild-card"></div>
      <div class="addchild-card">
        <h1 class="addchild-title">아이 등록</h1>
        <div>
          <!-- 아이 프로필 등록 -->
          <div class="addchild-input">
            <div class="avatar-upload">
              <div class="avatar-edit">
                <input
                  type="file"
                  id="child_profile"
                  ref="child_profile"
                  accept=".png, .jpg, .jpeg"
                  @change="uploadProfile"
                />
                <label class="upload-label" for="child_profile">
                  <div class="leaves-1"></div>
                  <div class="leaves-2"></div>
                  <div class="leaves-3"></div>
                  <div class="avatar-preview">
                    <div id="imagePreview"></div>
                  </div>
                  <div class="carrot"></div>
                </label>
              </div>
            </div>
          </div>
          <div class="addchild-input">
            <div class="half">
              <div class="half-l">
                <input
                  type="text"
                  id="child_name"
                  v-model="child.child_name"
                  required="required"
                  @keyup.enter="addChild"
                />
                <label for="child_name" class="addchild-input-label"
                  >이름</label
                >
                <div class="addchild-input-bar"></div>
              </div>
              <div class="half-r">
                <select
                  name="child_sex"
                  id="child_sex"
                  v-model="child.child_sex"
                  @keyup.enter="addChild"
                >
                  <option value="">선택</option>
                  <option value="남">남</option>
                  <option value="여">여</option>
                </select>
                <label for="child_sex" class="addchild-input-label">성별</label>
                <div class="addchild-input-bar"></div>
              </div>
            </div>
          </div>
          <div class="addchild-input">
            <div class="half">
              <div class="half-l">
                <select
                  name="child_area"
                  id="child_area"
                  v-model="child.child_area"
                  @keyup.enter="addChild"
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
                <label for="child_area" class="addchild-input-label"
                  >지역</label
                >
                <div class="addchild-input-bar"></div>
              </div>
              <div class="half-r">
                <input
                  type="text"
                  id="child_schoolName"
                  v-model="child.child_schoolName"
                  required="required"
                  @keyup.enter="addChild"
                />
                <label for="child_schoolName" class="addchild-input-label"
                  >학교명</label
                >
                <div class="addchild-input-bar"></div>
              </div>
            </div>
          </div>
          <div class="addchild-input">
            <input
              type="date"
              class="child_birth"
              v-model="child.child_birth"
              required="required"
              @keyup.enter="addChild"
            />
            <label for="child-birth" class="addchild-input-label"
              >생년월일</label
            >
            <div class="addchild-input-bar"></div>
          </div>
          <div class="addchild-input">
            <div class="half">
              <div class="half-l">
                <input
                  type="number"
                  class="child_height"
                  v-model="child.child_height"
                />
                <label for="child_height" class="addchild-input-label"
                  >키(cm)</label
                >
                <div class="addchild-input-bar"></div>
              </div>
              <div class="half-r">
                <input
                  type="number"
                  class="child_weight"
                  v-model="child.child_weight"
                />
                <label for="child_weight" class="addchild-input-label"
                  >몸무게(kg)</label
                >
                <div class="addchild-input-bar"></div>
              </div>
            </div>
          </div>
          <div class="addchild-button-container">
            <div class="button-box">
              <div class="carrot" @click="addChild()">
                <span>등록</span>
              </div>
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
            <p class="modal_content">아이 등록 완료!</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import "@/assets/css/User/AddChild.css";
import http from "@/api/http-common.js";
import router from "../../router";
import axios from "axios";
export default {
  data() {
    return {
      child: {
        child_parentId: "", // 부모 아이디
        child_area: "", // 아이 학교 지역코드
        child_schoolName: "", // 아이 학교 이름
        child_name: "", // 아이 이름
        child_sex: "", // 아이 성별
        child_birth: "", // 아이 생년월일
        child_height: "", // 아이 키
        child_weight: "", // 아이 몸무게
        child_profile: "", // 아이 프로필사진
        profile_file: "",
        profile_preview: "",
      },
      date: "",
      checkModalMessage1: "",
      checkModal1: false,
      checkModal2: false,
    };
  },
  created() {
    this.getToday();
  },
  methods: {
    getToday() {
      var today = new Date();
      var month = today.getMonth() + 1;
      var day = today.getDay();
      if (month < 10) month = "0" + month;
      if (day < 10) day = "0" + day;
      this.date = today.getFullYear() + "" + month + "" + day;
    },
    uploadProfile(e) {
      this.profile_file = e.target.files[0];
      this.child.child_profile = this.profile_file;
      this.profile_preview = URL.createObjectURL(this.profile_file);

      let preview = document.querySelector("#imagePreview");
      preview.style.backgroundImage = "url(" + `${this.profile_preview}` + ")";
    },
    addChild() {
      this.child.child_parentId = localStorage.getItem("parent_id");
      //   this.child.child_parentId = "parent-1";

      if (this.child.child_name == "") {
        this.checkModalMessage1 = "이름";
        this.checkModal1 = true;

        setTimeout(() => {
          this.checkModal1 = false;
        }, 800);

        return;
      }
      if (this.child.child_area == "") {
        this.checkModalMessage1 = "지역";
        this.checkModal1 = true;

        setTimeout(() => {
          this.checkModal1 = false;
        }, 800);

        return;
      }
      if (this.child.child_schoolName == "") {
        this.checkModalMessage1 = "학교명";
        this.checkModal1 = true;

        setTimeout(() => {
          this.checkModal1 = false;
        }, 800);

        return;
      }
      if (this.child.child_birth == "") {
        this.checkModalMessage1 = "생년월일";
        this.checkModal1 = true;

        setTimeout(() => {
          this.checkModal1 = false;
        }, 800);

        return;
      }
      if (this.child.child_sex == "") {
        this.checkModalMessage1 = "성별";
        this.checkModal1 = true;

        setTimeout(() => {
          this.checkModal1 = false;
        }, 800);

        return;
      }

      if (this.child.child_height == "") {
        this.child.child_height = 0;
      }
      if (this.child.child_weight == "") {
        this.child.child_weight = 0;
      }

      var formData = new FormData();
      formData.append("child_parentId", this.child.child_parentId);
      formData.append("child_area", this.child.child_area);
      formData.append("child_schoolName", this.child.child_schoolName);
      formData.append("child_name", this.child.child_name);
      formData.append("child_sex", this.child.child_sex);
      formData.append("child_birth", this.child.child_birth);
      formData.append("child_height", this.child.child_height);
      formData.append("child_weight", this.child.child_weight);
      formData.append("child_profile", this.child.child_profile);

      http.post(`/child/addChild`, formData).then(
        (res) => {
          axios
            .get(
              `http://j4b205.p.ssafy.io:8001/myapp/schoolfeeding/${this.date}/${this.child.child_schoolName}/${this.child.child_area}/`
              // `http://127.0.0.1:8001/myapp/schoolfeeding/${this.date}/${this.child.child_schoolName}/${this.child.child_area}/`
            )
            .then((res) => {
              var schoolFeeding = res.data;
              if (schoolFeeding.length !== 0) {
                var currentSchoolFeedingId = res.data[0].schoolfeeding_id;
                axios.get(
                  `http://j4b205.p.ssafy.io:8001/myapp/schoolfeedingdetail/${this.date}/${this.child.child_area}/${this.child.child_schoolName}/${currentSchoolFeedingId}/`
                  // `http://127.0.0.1:8001/myapp/schoolfeedingdetail/${this.date}/${this.child.child_area}/${this.child.child_schoolName}/${currentSchoolFeedingId}/`
                );
              }
            });
          localStorage.setItem("child_id", res.data);

          this.checkModal2 = true;

          setTimeout(() => {
            this.checkModal2 = false;
          }, 800);

          router.push({
            name: "SelectPreference",
          });
        },
        (err) => {
          console.log(err);
        }
      );
    },
  },
};
</script>

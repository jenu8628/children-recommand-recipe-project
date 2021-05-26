<template>
  <div class="FoodRecordDetail-container">
    <div class="back" @click="back">
      <i class="fas fa-chevron-circle-left"></i>
    </div>
    <div class="selectInfo-box">
      <div class="slider-container">
        <div
          class="slider-control left inactive"
          :id="'left'"
          @click="sliderControlAction"
        ></div>
        <div
          class="slider-control right"
          :id="'right'"
          @click="sliderControlAction"
        ></div>
        <ul class="slider-pagi"></ul>

        <div class="slider">
          <div class="slide slide-0 active">
            <div class="school-foodInfo">
              <div class="slide_content" style="top: 21%">
                <div class="slide_text">
                  <div>
                    <p style="margin-top: 10%" class="slide_text-desc">
                      칼로리 : {{ Kcal }} Kcal
                    </p>
                    <p class="slide_text-desc">
                      탄수화물 : {{ carbohydrate }} g
                    </p>
                    <p class="slide_text-desc">단백질 : {{ protein }} g</p>
                    <p class="slide_text-desc">지방 : {{ fat }} g</p>
                  </div>
                </div>
              </div>
              <div v-if="schoolOpen" class="SchoolFeedingDetail-container">
                <div class="cafeteria-title">
                  우리아이의 <br />
                  점심 급식표
                </div>
                <div class="lunch-box">
                  <div
                    class="cafeteria-lunch"
                    :key="idx"
                    v-for="(food, idx) in schoolFeedingDetailFood"
                  >
                    {{ food }}
                  </div>
                </div>
              </div>
              <div class="empty-school" v-else>
                <h1>오늘은 급식을 <br />먹지 않는 날이예요!</h1>
              </div>
            </div>
          </div>
          <div
            :class="'slide slide-' + `${idx + 1}`"
            v-for="(record, idx) in recordListToday"
            :key="idx"
          >
            <div class="info-image">
              <div>
                <div class="avatar-upload">
                  <div
                    class="avatar-preview"
                    :style="{
                      backgroundImage:
                        'url(data:image/jpeg;base64,' +
                        recordList[idx].foodRecord_media +
                        ')',
                    }"
                  ></div>
                </div>
              </div>
              <h2 class="slide_text-heading">
                {{ record.recipe_name }}
              </h2>
            </div>
            <div class="slide_content">
              <div class="slide_text">
                <div>
                  <p style="margin-top: 10%" class="slide_text-desc">
                    칼로리 : {{ record.recipe_kcal }} Kcal
                  </p>
                  <p class="slide_text-desc">
                    탄수화물 : {{ record.recipe_carbohydrate }} g
                  </p>
                  <p class="slide_text-desc">
                    단백질 : {{ record.recipe_protein }} g
                  </p>
                  <p class="slide_text-desc">
                    지방 : {{ record.recipe_fat }} g
                  </p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import http from "@/api/http-common";
import "@/assets/css/FoodRecord/FoodRecordHome.css";
// import FoodRecordHome from './FoodRecordHome'

export default {
  components: {
    // FoodRecordHome,
  },
  data() {
    return {
      date: "",
      schoolName: "",
      childArea: "",
      currentSchoolFeedingId: "",
      schoolFeeding: [],
      schoolFeedingDetailId: "",
      schoolFeedingDetailFood: [],
      schoolFeedingDetailFoodAllergy: [],
      year: this.$route.params.year,
      month: this.$route.params.month,
      day: this.$route.params.day,
      childName: "",
      dailyFood: this.$route.params.dailyFood,
      schoolOpen: true,
      recordList: [],
      recordListToday: [],
      slider: null,
      diff: "",
      curSlider: "",
      numOfSlides: "",
      animating: false,
      animTime: 500,
      pagination: null,
      Kcal: 0,
      carbohydrate: 0,
      protein: 0,
      fat: 0,
    };
  },
  mounted() {
    this.date = this.$route.params.today;
    this.getmychild();
    this.getMyMonthlyFoodRecord();
  },
  methods: {
    back() {
      this.$router.push({ name: "FoodRecord" });
    },
    getMyMonthlyFoodRecord() {
      http
        .get("/foodRecord/getMyMonthlyFoodRecord", {
          params: {
            foodRecord_childId: localStorage.getItem("child_id"),
            month: this.month,
            year: this.year,
          },
        })
        .then(
          (response) => {
            this.recordList = response.data.filter((it) => {
              return it.foodRecord_date.includes(
                this.year + "-" + this.month + "-" + this.day
              );
            });
            this.getRecordInfo();
            this.init();
          },
          (error) => {
            console.log(error);
          }
        );
    },
    init() {
      this.slider = document.querySelector(".slider");
      this.numOfSlides = this.recordList.length;
      this.pagination = document.querySelector(".slider-pagi");
      // this.pagination.style.visibility = "hidden";
      this.diff = 0;
      this.curSlide = 0;
      this.createBullets();
      this.sliderAction();
      this.sliderActionContainer();
      this.sliderPagiAction();
    },
    createBullets() {
      for (var i = 0; i < this.numOfSlides + 1; i++) {
        var new_li = document.createElement("li");
        new_li.setAttribute("class", "slider-pagi_elem");
        new_li.className += " slider-pagi_elem-" + i;
        new_li.setAttribute("page", i);
        if (!i) new_li.className += " active";
        this.pagination.append(new_li);
      }
    },
    manageControls() {
      document
        .querySelectorAll(".slider-control")
        .forEach((e) => e.classList.remove("inactive"));

      if (!this.curSlide)
        document.querySelector("#left").classList.add("inactive");
      if (this.curSlide === this.numOfSlides + 1)
        document.querySelector("#right").classList.add("inactive");
    },
    changeSlides(instant) {
      if (!instant) {
        this.animating = true;
        this.manageControls();
        this.slider.classList.add("animating");
        this.slider.style.top;
        document
          .querySelectorAll(".slide")
          .forEach((e) => e.classList.remove("active"));
        document
          .querySelector(".slide-" + this.curSlide)
          .classList.add("active");

        var _this = this;
        setTimeout(function () {
          _this.slider.classList.remove("animating");
          _this.animating = false;
        }, _this.animTime);
      }
      document
        .querySelectorAll(".slider-pagi_elem")
        .forEach((e) => e.classList.remove("active"));
      document
        .querySelector(".slider-pagi_elem-" + this.curSlide)
        .classList.add("active");
      this.slider.style.transform =
        "translate3d(" + -this.curSlide * 100 + "%, 0, 0)";
      this.diff = 0;
    },
    navigateLeft() {
      if (this.animating) return;
      if (this.curSlide > 0) {
        this.curSlide--;
      }
      this.changeSlides();
    },
    navigateRight() {
      if (this.animating) return;
      if (this.curSlide < this.numOfSlides) this.curSlide++;
      this.changeSlides();
    },
    mouseEventHandler(e, startX, winW) {
      var x = e.pageX;
      this.diff = ((startX - x) / winW) * 70;

      if (
        (!this.curSlide && this.diff < 0) ||
        (this.curSlide === this.numOfSlides && this.diff > 0)
      )
        this.diff /= 2;
      this.slider.style.transform =
        "translate3d(" + (-this.curSlide * 100 - this.diff) + "%, 0, 0)";
    },

    touchEventHandler(e, startX, winW) {
      var x = e.pageX;
      this.diff = ((startX - x) / winW) * 70;

      if (
        (!this.curSlide && this.diff < 0) ||
        (this.curSlide === this.numOfSlides && this.diff > 0)
      )
        this.diff /= 2;
      this.slider.style.transform =
        "translate3d(" + (-this.curSlide * 100 - this.diff) + "%, 0, 0)";
    },
    sliderAction() {
      this.slider.addEventListener("mousedown", (e) => {
        if (this.animating) return;
        window.clearTimeout(this.autoSlideTimeout);

        var startX = e.pageX;
        var winW = window.innerWidth;
        this.diff = 0;
        this.slider.addEventListener(
          "mousemove",
          this.mouseEventHandler(e, startX, winW)
        );
        this.slider.addEventListener(
          "touchmove",
          this.touchEventHandler(e, startX, winW)
        );
      });

      this.slider.addEventListener("touchstart", (e) => {
        if (this.animating) return;
        window.clearTimeout(this.autoSlideTimeout);

        var startX = e.touches[0].pageX;
        var winW = window.innerWidth;
        this.diff = 0;
        this.slider.addEventListener("touchmove", (e) => {
          var x = e.touches[0].pageX;
          this.diff = ((startX - x) / winW) * 70;
          if (
            (!this.curSlide && this.diff < 0) ||
            (this.curSlide === this.numOfSlides && this.diff > 0)
          )
            this.diff /= 2;
          this.slider.style.transform =
            "translate3d(" + (-this.curSlide * 100 - this.diff) + "%, 0, 0)";
        });
      });
    },
    sliderEvent_container() {
      // mouse
      this.slider.addEventListener("mouseup", () => {
        this.slider.removeEventListener("mousemove", this.mouseEventHandler);
        this.slider.removeEventListener("touchmove", this.touchEventHandler);
        if (this.animating) return;
        if (!this.diff) {
          this.changeSlides(true);
          return;
        }
        if (this.diff > -8 && this.diff < 8) {
          this.changeSlides();
          return;
        }
        if (this.diff <= -8) {
          this.navigateLeft();
        }
        if (this.diff >= 8) {
          this.navigateRight();
        }
      });

      // touch
      this.slider.addEventListener("touchend", () => {
        this.slider.removeEventListener("mousemove", () => {});
        this.slider.removeEventListener("touchmove", () => {});
        if (this.animating) return;
        if (!this.diff) {
          this.changeSlides(true);
          return;
        }
        if (this.diff > -8 && this.diff < 8) {
          this.changeSlides();
          return;
        }
        if (this.diff <= -8) {
          this.navigateLeft();
        }
        if (this.diff >= 8) {
          this.navigateRight();
        }
      });
    },
    sliderActionContainer() {
      this.slider.addEventListener("mouseup", this.sliderEvent_container());
    },
    sliderControlAction(e) {
      if (e.target.id == "left") {
        this.navigateLeft();
      } else {
        this.navigateRight();
      }
    },
    sliderPagiAction() {
      document.querySelectorAll(".slider-pagi_elem").forEach((even) =>
        even.addEventListener("click", (e) => {
          this.curSlide = e.target.getAttribute("page");
          this.changeSlides();
        })
      );
    },
    getSchoolInfo() {
      axios
        .get(
          `http://j4b205.p.ssafy.io:8001/myapp/schoolfeeding/${this.date}/${this.schoolName}/${this.childArea}/`
          // `http://127.0.0.1:8001/myapp/schoolfeeding/${this.date}/${this.schoolName}/${this.childArea}/`
        )
        .then((res) => {
          this.schoolFeeding = res.data[0];
          if (res.data.length !== 0) {
            this.Kcal = parseFloat(
              this.schoolFeeding.schoolfeeding_kcal.split(" ")[0]
            );
            this.carbohydrate = this.schoolFeeding.schoolfeeding_carbohydrate;
            this.protein = this.schoolFeeding.schoolfeeding_protein;
            this.fat = this.schoolFeeding.schoolfeeding_fat;
            this.currentSchoolFeedingId = res.data[0].schoolfeeding_id;
            // 급식이 존재할 때만 식단표불러오기
            axios
              .get(
                `http://j4b205.p.ssafy.io:8001/myapp/schoolfeedingdetail/${this.date}/${this.childArea}/${this.schoolName}/${this.currentSchoolFeedingId}/`
                // `http://127.0.0.1:8001/myapp/schoolfeedingdetail/${this.date}/${this.childArea}/${this.schoolName}/${this.currentSchoolFeedingId}/`
              )
              .then((res) => {
                this.schoolOpen = true;
                this.schoolFeedingDetailId = res.data[0].schoolFeedingDetail_id;
                if (res.data[0].schoolfeedingdetail_foodno1 !== "null") {
                  this.schoolFeedingDetailFood.push(
                    res.data[0].schoolfeedingdetail_foodno1
                  );
                  this.schoolFeedingDetailFoodAllergy.push(
                    res.data[0].schoolfeedingdetail_foodno1allergy
                  );
                }
                if (res.data[0].schoolfeedingdetail_foodno2 !== "null") {
                  this.schoolFeedingDetailFood.push(
                    res.data[0].schoolfeedingdetail_foodno2
                  );
                  this.schoolFeedingDetailFoodAllergy.push(
                    res.data[0].schoolfeedingdetail_foodno2allergy
                  );
                }
                if (res.data[0].schoolfeedingdetail_foodno3 !== "null") {
                  this.schoolFeedingDetailFood.push(
                    res.data[0].schoolfeedingdetail_foodno3
                  );
                  this.schoolFeedingDetailFoodAllergy.push(
                    res.data[0].schoolfeedingdetail_foodno3allergy
                  );
                }
                if (res.data[0].schoolfeedingdetail_foodno4 !== "null") {
                  this.schoolFeedingDetailFood.push(
                    res.data[0].schoolfeedingdetail_foodno4
                  );
                  this.schoolFeedingDetailFoodAllergy.push(
                    res.data[0].schoolfeedingdetail_foodno4allergy
                  );
                }
                if (res.data[0].schoolfeedingdetail_foodno5 !== "null") {
                  this.schoolFeedingDetailFood.push(
                    res.data[0].schoolfeedingdetail_foodno5
                  );
                  this.schoolFeedingDetailFoodAllergy.push(
                    res.data[0].schoolfeedingdetail_foodno5allergy
                  );
                }
                if (res.data[0].schoolfeedingdetail_foodno6 !== "null") {
                  this.schoolFeedingDetailFood.push(
                    res.data[0].schoolfeedingdetail_foodno6
                  );
                  this.schoolFeedingDetailFoodAllergy.push(
                    res.data[0].schoolfeedingdetail_foodno6allergy
                  );
                }
                if (res.data[0].schoolfeedingdetail_foodno7 !== "null") {
                  this.schoolFeedingDetailFood.push(
                    res.data[0].schoolfeedingdetail_foodno7
                  );
                  this.schoolFeedingDetailFoodAllergy.push(
                    res.data[0].schoolfeedingdetail_foodno7allergy
                  );
                }
                if (res.data[0].schoolfeedingdetail_foodno8 !== "null") {
                  this.schoolFeedingDetailFood.push(
                    res.data[0].schoolfeedingdetail_foodno8
                  );
                  this.schoolFeedingDetailFoodAllergy.push(
                    res.data[0].schoolfeedingdetail_foodno8allergy
                  );
                }
                if (res.data[0].schoolfeedingdetail_foodno9 !== "null") {
                  this.schoolFeedingDetailFood.push(
                    res.data[0].schoolfeedingdetail_foodno9
                  );
                  this.schoolFeedingDetailFoodAllergy.push(
                    res.data[0].schoolfeedingdetail_foodno9allergy
                  );
                }
                if (res.data[0].schoolfeedingdetail_foodno10 !== "null") {
                  this.schoolFeedingDetailFood.push(
                    res.data[0].schoolfeedingdetail_foodno10
                  );
                  this.schoolFeedingDetailFoodAllergy.push(
                    res.data[0].schoolfeedingdetail_foodno10allergy
                  );
                }
              })
              .catch((err) => {
                console.error(err);
              });
          } else {
            this.schoolOpen = false;
          }
        })
        .catch(() => {
          this.schoolFeeding = null;
          this.schoolFeedingDetail = null;
        });
    },
    // 아이조회
    getmychild() {
      http
        .get("/child/getMyChild", {
          params: {
            child_id: localStorage.getItem("child_id"),
          },
        })
        .then((res) => {
          this.childArea = res.data.child_area;
          this.childName = res.data.child_name;
          this.schoolName = res.data.child_schoolName;
          this.getSchoolInfo();
        });
    },
    // 당일 먹은 식단 조회
    getRecordInfo() {
      for (let i = 0; i < this.recordList.length; i++) {
        http
          .get(`/recipe/getRecipe/${this.recordList[i].foodRecord_recipeId}`)
          .then((res) => {
            this.recordListToday.push(res.data);
          });
      }
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
  },
  created() {},
};
</script>

<style scoped>
@font-face {
  font-family: "BMDOHYEON";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_one@1.0/BMDOHYEON.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}
.FoodRecordDetail-container {
  position: relative;
  width: 100vw;
  font-weight: 300;
  /* height: 100%; */
  display: flex;
  flex-direction: column;
  align-items: center;
}
.FoodRecordDetail-container .back {
  position: fixed;
  width: 10vw;
  height: 10vw;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 3rem;
  color: #40aa57;
  z-index: 999;
  top: 20px;
  left: 20px;
}
.FoodRecordDetail-container .school-foodInfo {
  position: relative;
  top: 15%;
  height: 85%;
  width: 100vw;
  font-weight: 300;
  /* height: 100%; */
  display: flex;
  flex-direction: column;
  align-items: center;
}
.FoodRecordDetail-container .school-foodInfo .empty-school {
  position: absolute;
  height: 80%;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}
.FoodRecordDetail-container .school-foodInfo .record-title {
  font-size: 1.8em;
  margin-bottom: 1.2em;
}
.FoodRecordDetail-container .school-foodInfo .SchoolFeeding-contailner {
  display: grid;
  width: 80%;
  grid-template-columns: 1fr 1fr 1fr;
}
.FoodRecordDetail-container .SchoolFeeding-contailner .Intake-title {
  border-bottom: 1px solid black;
  margin-bottom: 1em;
}
.FoodRecordDetail-container .SchoolFeeding-contailner > div {
  height: 3.8vh;
}
.FoodRecordDetail-container .SchoolFeedingDetail-container {
  display: flex;
  width: 80vw;
  flex-direction: column;
  align-items: center;
  background: white;
  border-radius: 10px;
}
.FoodRecordDetail-container .SchoolFeedingDetail-container .cafeteria-title {
  /* text-align: center;
  margin-bottom: 1.2em; */
  margin-bottom: 5%;
  font-size: 1.8em;
  width: 192px;
  height: 192px;
  position: relative;
  border-radius: 100%;
  border: 6px solid #f8f8f8;
  box-shadow: 0px 2px 4px 0px rgb(0 0 0 / 10%);
  background-size: cover;
  background-repeat: no-repeat;
  background-position: center;
  display: flex;
  justify-content: center;
  align-items: center;
}
.FoodRecordDetail-container .SchoolFeedingDetail-container .cafeteria-lunch {
  display: flex;
  justify-content: center;
  font-size: 1.3em;
  /* 줄간격 */
  line-height: 2em;
}
.FoodRecordDetail-container .SchoolFeedingDetail-container .lunch-box {
  overflow: scroll;
  height: 250px;
}
</style>
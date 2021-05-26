<template>
  <div class="selectInfo-container">
    <div class="selectInfo-box">
      <button class="info-close" @click="close"></button>
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
            <div class="info-image">
              <div class="info-image-wrap">
                <div class="info-left-half-first"></div>
                <div class="info-right-half-first"></div>
              </div>
              <div
                class="slide__text-heading"
                style="font-family: 'Jal_Onuel';"
              >
                돼지고기
              </div>
            </div>
            <div class="slide__content">
              <div class="slide__text">
                <p class="slide__text-desc">
                  우리 아이가 <br />
                  위의 재료를 얼마나 <br />
                  좋아하는지 알려주세요!
                </p>
              </div>
            </div>
          </div>
          <div class="slide slide-1">
            <div class="info-image">
              <div class="info-image-wrap">
                <div class="info-left-half"></div>
                <div class="info-right-half"></div>
              </div>
              <p class="slide__text-heading" style="font-family: 'Jal_Onuel';">
                돼지고기
              </p>
            </div>
            <div class="slide__content">
              <div class="slide__text">
                <p class="slide__text-desc_second">
                  왼쪽은 -1, 오른쪽은 +1 <br />
                </p>
                <p class="slide__text-desc_second_c">
                  0 ~ 5 범위 내의 <br />선호도를 입력받습니다.
                </p>
              </div>
            </div>
          </div>

          <!--  -->
          <div class="slide slide-2">
            <div class="po_ani">
              <div
                class="animate__animated animate__bounce animate__infinite animate__slow"
              >
                <p class="slide_clear">완료</p>
              </div>
            </div>
            <div class="slide__content">
              <div class="slide__text">
                <p class="slide__text-desc_clear">
                  선호도를 모두 입력하신 후, <br />
                  완료 버튼을 누르면 <br /><br />
                  우리 아이를 위한 추천 레시피가 생성됩니다!!
                </p>
              </div>
            </div>
          </div>
          <!--  -->
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import "@/assets/css/Main/TodayTip.css";

export default {
  data() {
    return {
      slider: null,
      diff: "",
      curSlider: "",
      numOfSlides: "",
      animating: false,
      animTime: 500,
      autoSlideTimeout: null,
      autoSlideDelay: 6000,
      pagination: null,
    };
  },
  created() {},
  mounted() {
    this.init();
    this.createBullets();
    this.autoSlide();
    this.sliderAction();
    this.sliderActionContainer();
    this.sliderPagiAction();
  },
  updated() {},
  methods: {
    init() {
      this.slider = document.querySelector(".slider");
      this.numOfSlides = document.querySelectorAll(".slide").length - 1;
      this.pagination = document.querySelector(".slider-pagi");

      this.diff = 0;
      this.curSlide = 0;
    },
    close() {
      this.$emit("update");
    },
    createBullets() {
      for (var i = 0; i < this.numOfSlides + 1; i++) {
        var new_li = document.createElement("li");
        new_li.setAttribute("class", "slider-pagi__elem");
        new_li.className += " slider-pagi__elem-" + i;
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
      if (this.curSlide === this.numOfSlides)
        document.querySelector("#right").classList.add("inactive");
    },
    autoSlide() {
      var _this = this;
      this.autoSlideTimeout = setTimeout(function() {
        _this.curSlide++;
        if (_this.curSlide > _this.numOfSlides) _this.curSlide = 0;
        _this.changeSlides();
      }, _this.autoSlideDelay);
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
        setTimeout(function() {
          _this.slider.classList.remove("animating");
          _this.animating = false;
        }, _this.animTime);
      }
      window.clearTimeout(this.autoSlideTimeout);
      document
        .querySelectorAll(".slider-pagi__elem")
        .forEach((e) => e.classList.remove("active"));
      document
        .querySelector(".slider-pagi__elem-" + this.curSlide)
        .classList.add("active");
      this.slider.style.transform =
        "translate3d(" + -this.curSlide * 100 + "%, 0, 0)";
      this.diff = 0;
      this.autoSlide();
    },
    navigateLeft() {
      if (this.animating) return;
      if (this.curSlide > 0) this.curSlide--;
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

        // this.slider.addEventListener("mousemove", (e) => {
        //   var x = e.pageX;
        //   this.diff = ((startX - x) / winW) * 70;
        //   // console.log("diff >>>> " + this.diff);
        //   if (
        //     (!this.curSlide && this.diff < 0) ||
        //     (this.curSlide === this.numOfSlides && this.diff > 0)
        //   )
        //     this.diff /= 2;
        //   this.slider.style.transform =
        //     "translate3d(" + (-this.curSlide * 100 - this.diff) + "%, 0, 0)";
        // });

        // this.slider.addEventListener("touchmove", (e) => {
        //   var x = e.touches[0].pageX;
        //   this.diff = ((startX - x) / winW) * 70;
        //   if (
        //     (!this.curSlide && this.diff < 0) ||
        //     (this.curSlide === this.numOfSlides && this.diff > 0)
        //   )
        //     this.diff /= 2;
        //   this.slider.style.transform =
        //     "translate3d(" + (-this.curSlide * 100 - this.diff) + "%, 0, 0)";
        // });

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

        // this.slider.addEventListener("mousemove", (e) => {
        //   var x = e.pageX;
        //   this.diff = ((startX - x) / winW) * 70;
        //   if (
        //     (!this.curSlide && this.diff < 0) ||
        //     (this.curSlide === this.numOfSlides && this.diff > 0)
        //   )
        //     this.diff /= 2;
        //   this.slider.style.transform =
        //     "translate3d(" + (-this.curSlide * 100 - this.diff) + "%, 0, 0)";
        // });

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
        // var test = this.slider.cloneNode(true);

        // console.log("test >>> " + test);
        // console.log("test.parentNode >>> " + test.parentNode);

        // this.slider.removeEventListener("mousemove", () => {
        //   console.log("remove-mouse!");
        // });

        // var startX = e.pageX;
        // var _startX = e.touches[0].pageX;
        // var winW = window.innerWidth;

        this.slider.removeEventListener("mousemove", this.mouseEventHandler);

        this.slider.removeEventListener("touchmove", this.touchEventHandler);

        // this.slider.removeEventListener("touchmove", () => {
        //   console.log("remove-touch!");
        // });

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
        this.slider.removeEventListener("mousemove", () => {
          console.log("remove-mouse!");
        });
        this.slider.removeEventListener("touchmove", () => {
          console.log("remove-touch!");
        });
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
      document.querySelectorAll(".slider-pagi__elem").forEach((even) =>
        even.addEventListener("click", (e) => {
          this.curSlide = e.target.getAttribute("page");
          this.changeSlides();
        })
      );
    },
  },
};
</script>

<style>
.selectInfo-container {
  width: 100vw;
  height: 100vh;
  display: flex;
  top: 0;
  left: 0;
  position: fixed;
  justify-content: center;
  align-items: center;
  z-index: 5;
  background: rgba(0, 0, 0, 0.7);
  backdrop-filter: saturate(180%) blur(3px);
  font-family: "Jal_Onuel";
}

.selectInfo-container .selectInfo-box {
  border-radius: 10px;
  max-width: 400px;
  width: 70%;
  height: 75%;
  margin: auto;
  overflow: hidden;
  display: flex;
  flex-wrap: wrap;
  z-index: 11;
  background-color: ivory;
}
.selectInfo-container .selectInfo-box > div {
  width: 100%;
  overflow: hidden;
}
.selectInfo-container .selectInfo-box .detail {
  height: 45%;
  overflow: scroll;
}
.selectInfo-container .selectInfo-box .button {
  height: 10%;
  display: flex;
  justify-content: space-around;
  align-items: center;
}

.info-image {
  top: 15%;
  width: 100%;
  height: 50%;
  /* background-color: black; */
}

.info-left-half,
.info-right-half {
  height: 100%;
  width: 50%;
}

.info-left-half {
  border-bottom-left-radius: 90px;
  border-top-left-radius: 90px;
  /* background-color: red; */
  border-right: white 1px dashed;
  float: left;
}
.info-right-half {
  border-bottom-right-radius: 90px;
  border-top-right-radius: 90px;
  /* background-color: blue; */
  /* border-left: white 1px solid; */
  float: right;
}

.info-left-half-first {
  border-bottom-left-radius: 90px;
  border-top-left-radius: 90px;
  float: left;
}
.info-right-half-first {
  border-bottom-right-radius: 90px;
  border-top-right-radius: 90px;
  float: right;
}
.info-image-wrap {
  border: 4px solid white;
  -moz-box-shadow: 2px 3px 3px rgba(0, 0, 0, 0.2);
  -webkit-box-shadow: 2px 3px 3px rgba(0, 0, 0, 0.2);
  box-shadow: 2px 3px 3px rgba(0, 0, 0, 0.2);
}
.info-image-wrap {
  margin-left: 7.4%;
  margin-top: 17%;
  z-index: 1;
  width: 85%;
  height: 71%;
  /* display: block; */
  display: inline-block;
  /* margin: 0 auto; */
  /* margin-bottom: 1.0em; */
  -moz-border-radius: 50%;
  -webkit-border-radius: 50%;
  border-radius: 50%;
  overflow: hidden;
  line-height: 0;
  text-align: center;
  /* background-color: black; */
  background-image: url("../../assets/img/category/c01.jpeg");
  background-size: cover;
  /* transform: rotate(45deg); */
}
.info-ingerdient_name {
  font-style: bold;
  font-size: 40px;
  text-align: center;
  height: 1.5em;
  margin: 0 auto;
  /* background-color: blue; */
}
.info-close {
  position: fixed;
  z-index: 10;
  top: 14%;
  left: 77.5%;
  width: 1em;
  height: 1em;
  background-image: url("../../assets/img/close.png");
  background-size: cover;
}
.info-left-icon {
  top: 50%;
}

/* ------------- slider */
*,
*:before,
*:after {
  box-sizing: border-box;
  margin: 0;
  padding: 0;
}

/* html, body {
  font-size: 62.5%;
  height: 100%;
  overflow: hidden;
}

body {
  background: #000;
} */

svg {
  display: block;
  overflow: visible;
}

.slider-container {
  position: relative;
  height: 100%;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
  cursor: all-scroll;
}

.slider-control {
  z-index: 2;
  position: absolute;
  top: 0;
  width: 12%;
  height: 100%;
  transition: opacity 0.3s;
  will-change: opacity;
  opacity: 0;
}
.slider-control.inactive:hover {
  cursor: auto;
}
.slider-control:not(.inactive):hover {
  opacity: 1;
  cursor: pointer;
}
.slider-control.left {
  left: 0;
  background: linear-gradient(
    to right,
    rgba(0, 0, 0, 0.18) 0%,
    rgba(0, 0, 0, 0) 100%
  );
}
.slider-control.right {
  right: 0;
  background: linear-gradient(
    to right,
    rgba(0, 0, 0, 0) 0%,
    rgba(0, 0, 0, 0.18) 100%
  );
}

.slider-pagi {
  position: absolute;
  z-index: 3;
  left: 50%;
  bottom: 1.6rem;
  transform: translateX(-50%);
  font-size: 0;
  list-style-type: none;
}
.slider-pagi__elem {
  position: relative;
  display: inline-table;
  vertical-align: top;
  width: 0.6rem;
  height: 0.6rem;
  margin: 0 0.5rem;
  border-radius: 50%;
  border: 2px solid black;
  opacity: 0.9;
  cursor: pointer;
}
.slider-pagi__elem:before {
  content: "";
  position: absolute;
  left: 50%;
  top: 50%;
  width: 0.6rem;
  height: 0.6rem;
  background: black;
  border-radius: 50%;
  transition: transform 0.3s;
  transform: translate(-50%, -50%) scale(0);
}

.slider-pagi__elem.active:before {
  transform: translate(-50%, -50%) scale(1);
}

.slider-pagi__elem.active:after {
  transform: translate(-50%, -50%) scale(0);
}

.slider {
  z-index: 1;
  position: relative;
  height: 100%;
}
.slider.animating {
  transition: transform 0.5s;
  will-change: transform;
}
.slider.animating .slide__bg {
  transition: transform 0.5s;
  will-change: transform;
}

.slide {
  position: absolute;
  top: 0;
  width: 100%;
  height: 100%;
  overflow: hidden;
}
.slide.active .slide__overlay,
.slide.active .slide__text {
  opacity: 1;
  transform: translateX(0);
}
.slide__bg {
  position: absolute;
  top: 0;
  left: -50%;
  width: 100%;
  height: 100%;
  background-size: cover;
  will-change: transform;
}
.slide:nth-child(1) {
  left: 0;
}
.slide:nth-child(1) .slide__bg {
  left: 0;
  background-image: url("https://s3-us-west-2.amazonaws.com/s.cdpn.io/142996/3d-slider-3.jpg");
}
.slide:nth-child(1) .slide__overlay-path {
  fill: #e99c7e;
}
@media (max-width: 991px) {
  .slide:nth-child(1) .slide__text {
    background-color: rgba(233, 156, 126, 0.8);
  }
}
.slide:nth-child(2) {
  left: 100%;
}
.slide:nth-child(2) .slide__bg {
  left: -50%;
  background-image: url("https://s3-us-west-2.amazonaws.com/s.cdpn.io/142996/3d-slider-2.jpg");
}
.slide:nth-child(2) .slide__overlay-path {
  fill: #e1ccae;
}
@media (max-width: 991px) {
  .slide:nth-child(2) .slide__text {
    background-color: rgba(225, 204, 174, 0.8);
  }
}
.slide:nth-child(3) {
  left: 200%;
}
.slide:nth-child(3) .slide__bg {
  left: -100%;
  background-image: url("https://s3-us-west-2.amazonaws.com/s.cdpn.io/142996/3d-slider-1.jpg");
}
.slide:nth-child(3) .slide__overlay-path {
  fill: #adc5cd;
}
@media (max-width: 991px) {
  .slide:nth-child(3) .slide__text {
    background-color: rgba(173, 197, 205, 0.8);
  }
}
.slide:nth-child(4) {
  left: 300%;
}
.slide:nth-child(4) .slide__bg {
  left: -150%;
  background-image: url("https://s3-us-west-2.amazonaws.com/s.cdpn.io/142996/3d-slider-4.jpg");
}
.slide:nth-child(4) .slide__overlay-path {
  fill: #cbc6c3;
}
@media (max-width: 991px) {
  .slide:nth-child(4) .slide__text {
    background-color: rgba(203, 198, 195, 0.8);
  }
}
.slide__content {
  position: absolute;
  top: 25%;
  left: 0;
  width: 100%;
  height: 100%;
  opacity: 0.9;
}
.slide__overlay {
  position: absolute;
  bottom: 0;
  left: 0;
  height: 100%;
  min-height: 810px;
  transition: transform 0.5s 0.5s, opacity 0.2s 0.5s;
  will-change: transform, opacity;
  transform: translate3d(-20%, 0, 0);
  opacity: 0;
}
@media (max-width: 991px) {
  .slide__overlay {
    display: none;
  }
}
.slide__overlay path {
  opacity: 0.8;
}
.slide__text {
  position: absolute;
  width: 25%;
  bottom: 15%;
  left: 12%;
  color: #fff;
  border-radius: 4.5%;
  transition: transform 0.5s 0.8s, opacity 0.5s 0.8s;
  will-change: transform, opacity;
  transform: translateY(-50%);
  opacity: 0;
  box-shadow: 0 -0.2em 0.4em gray;
}
@media (max-width: 991px) {
  .slide__text {
    left: 0;
    bottom: 0;
    width: 100%;
    height: 20rem;
    text-align: center;
    transform: translateY(50%);
    transition: transform 0.5s 0.5s, opacity 0.5s 0.5s;
    padding: 0 1rem;
  }
}
.slide__text-heading {
  z-index: 1;
  font-family: "Polar", Helvetica, Arial, sans-serif;
  font-size: 2rem;
  height: 17%;
  margin-bottom: 1.3rem;
  top: 2%;
  text-align: center;
}
@media (max-width: 370px) {
  .slide__text-heading {
    line-height: 20rem;
    font-size: 3.5rem;
  }
}
.slide__text-desc {
  /* z-index: 1; */
  top: 8%;
  font-size: 1.3rem;
  /* margin-bottom: 1.5rem; */
  text-align: center;
}
.slide__text-desc_second {
  top: 8%;
  font-size: 1.3rem;
  /* margin-bottom: 1.5rem; */
  text-align: center;
  color: black;
}
.slide__text-desc_second_c {
  top: 16%;
  font-size: 0.8rem;
  /* margin-bottom: 1.5rem; */
  text-align: center;
  color: black;
}
.slide_clear {
  /* top: 35%; */
  vertical-align: center;
  font-size: 3.9rem;
  /* margin-bottom: 1.5rem; */
  text-align: center;
  color: green;
}
.slide_clear_container {
  animation: bounce;
  --animate-repeat: 2;
}
.slide__text-desc_clear {
  top: 3%;
  vertical-align: center;
  font-size: 1.1rem;
  /* margin-bottom: 1.5rem; */
  text-align: center;
  color: black;
}
/* === */
.po_ani {
  top: 35%;
}
</style>

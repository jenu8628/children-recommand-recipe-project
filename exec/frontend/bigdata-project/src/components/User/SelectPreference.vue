<template>
  <div class="select_container">
    <div class="select_menu">
      <SelectPreferenceInfo v-if="info" @update="closeModal" />
      <p class="prefer_title">선호 재료를 선택하세요!</p>
    </div>
    <div class="select_p_container">
      <ul>
        <li v-for="(ingredient, idx) in ingredientList" :key="idx">
          <div class="rating rating_hidden" :id="'rating-' + `${idx}`">
            {{ preferRatingList[idx].rating }}
          </div>
          <div
            class="image-wrap"
            :style="{
              backgroundImage:
                'url(' + ingredient.foodIngredient_img + ')',
            }"
          >
            <div
              class="left-half"
              :id="ingredient.foodIngredient_id"
              :value="idx"
              @click="ingredientOnClick_left(ingredient.foodIngredient_id, idx)"
            ></div>
            <div
              class="right-half"
              :id="ingredient.foodIngredient_id"
              :value="idx"
              @click="
                ingredientOnClick_right(ingredient.foodIngredient_id, idx)
              "
            ></div>
          </div>
          <h4
            :class="ingredient.foodIngredient_id"
            :id="ingredient.foodIngredient_name"
          >
            {{ ingredient.foodIngredient_name }}
          </h4>
        </li>
      </ul>
    </div>
    <div class="select_btn_container">
      <div class="btn-box">
        <div class="select_btn" @click="addPreference()">완료</div>
        <div class="leaves-1"></div>
        <div class="leaves-2"></div>
        <div class="leaves-3"></div>
      </div>
    </div>
    <Loading v-if="modalShow" />
  </div>
</template>

<script>
import http from "@/api/http-common";
import SelectPreferenceInfo from "../User/SelectPreferenceInfo";
import Loading from "../User/Loading";
import axios from "axios";

export default {
  components: {
    SelectPreferenceInfo: SelectPreferenceInfo,
    Loading: Loading,
  },
  data() {
    return {
      info: true,
      ingredientList: [],
      categoryList: [],
      preference_foodIngredientIdList: [],
      preferRatingList: [],
      modalShow: false,
    };
  },
  created() {
    http.get("/recipe/getAllFoodCategory").then(
      (response) => {
        var data = response.data;
        this.categoryList = data;
      },
      (error) => {
        console.log(error);
      }
    );
    http.get("/recipe/getAllFoodIngredient").then(
      (response) => {
        console.log(response.data)
        var data = response.data;
        this.ingredientList = data;
        this.createPreferRatingList();
      },
      (error) => {
        console.log(error);
      }
    );
  },
  mounted() {},
  methods: {
    closeModal() {
      this.info = false;
    },
    createPreferRatingList() {
      // preferRatingList init
      // 모든 식재료 id에 rating 0 넣어서 초기화 [{foodIngredient_id:rating(0)}]
      for (var i = 0; i < this.ingredientList.length; i++) {
        this.ingredientList[i].foodIngredient_img =  require(`@/assets/img/food/img_${i+1}.jpg`)
        this.preferRatingList.push({
          foodIngredient_id: this.ingredientList[i].foodIngredient_id,

          rating: 1,
        });
      }
    },
    ingredientOnClick_left(foodIngredient_id, idx) {
      const rating = this.preferRatingList.find((it) =>
        it.foodIngredient_id.includes(foodIngredient_id)
      ).rating;

      if (rating > 0) {
        this.preferRatingList.find((it) =>
          it.foodIngredient_id.includes(foodIngredient_id)
        ).rating = rating - 1;
      }

      const rating_count = document.querySelector(`#rating-${idx}`);
      rating_count.classList.remove("rating_hidden");
      setTimeout(() => rating_count.classList.add("rating_hidden"), 500);
    },
    ingredientOnClick_right(foodIngredient_id, idx) {
      const rating = this.preferRatingList.find((it) =>
        it.foodIngredient_id.includes(foodIngredient_id)
      ).rating;

      if (rating < 5) {
        this.preferRatingList.find((it) =>
          it.foodIngredient_id.includes(foodIngredient_id)
        ).rating = rating + 1;
      }

      const rating_count = document.querySelector(`#rating-${idx}`);
      rating_count.classList.remove("rating_hidden");
      setTimeout(() => rating_count.classList.add("rating_hidden"), 500);
    },
    async addPreference() {
      this.modalShow = true;

      var jsonData = JSON.stringify(this.preferRatingList);
      const formData = new FormData();
      formData.append("preference_childId", localStorage.getItem("child_id"));
      formData.append("preference_foodIngredientList", jsonData);
      await http.post("/child/addPreferenceForChild", formData).then(
        () => {},
        (error) => {
          console.log(error);
        }
      );
      await axios
        .post(
          // `http://j4b205.p.ssafy.io:8001/recommendation/playRecommendationAlgo`
          `http://127.0.0.1:8000/recommendation/playRecommendationAlgo`
        )
        .then(() => {
          this.modalShow = false;
          setTimeout(() => {
            this.$router.push("/");
            location.reload();
          }, 500);
        });
    },
  },
};
</script>

<style scoped>
@font-face {
  font-family: "Jal_Onuel";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-10-21@1.0/Jal_Onuel.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}

.select_container {
  font-family: "Jal_Onuel";
}

.prefer_title {
  padding-top: 4%;
  padding-bottom: 5%;
  margin: 0 auto;
  text-align: center;
  position: relative;
  font-family: "Jal_Onuel";
  font-size: 20px;
  font-weight: bold;
}
.select_container {
  height: 100%;
}
.select_menu h3 {
  text-align: center;
  color: black;
  font-size: 23px;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}
ul {
  width: 90%;
  margin: 0 auto;
  /* padding: 0.01em; */
  padding-left: 0;
  padding-right: 0;
  /* background-color: blue; */
  overflow: hidden;
  *zoom: 1;
}
ul .image-wrap {
  z-index: 1;
  width: 6em;
  height: 6em;
  /* display: block; */
  display: inline-block;
  margin: 0 auto;
  /* margin-bottom: 1.0em; */
  -moz-border-radius: 50%;
  -webkit-border-radius: 50%;
  border-radius: 50%;
  overflow: hidden;
  /* line-height: 0; */
  /* text-align: center; */
  /* background-color: black; */
  background-size: cover;
  /* transform: rotate(45deg); */
}

ul .image-wrap {
  border: 4px solid white;
  -moz-box-shadow: 2px 3px 3px rgba(0, 0, 0, 0.2);
  -webkit-box-shadow: 2px 3px 3px rgba(0, 0, 0, 0.2);
  box-shadow: 2px 3px 3px rgba(0, 0, 0, 0.2);
}

.left-half,
.right-half {
  height: 100%;
  width: 50%;
}

.left-half {
  border-bottom-left-radius: 90px;
  border-top-left-radius: 90px;
  /* background-color: red; */
  border-right: white 1.5px dashed;
  float: left;
}
.right-half {
  border-bottom-right-radius: 90px;
  border-top-right-radius: 90px;
  /* background-color: blue; */
  /* border-left: white 1px solid; */
  float: right;
}

ul .rating {
  position: absolute;
  width: auto;
  text-align: right;
  color: green;
}

ul .rating_hidden {
  visibility: hidden;
}

ul li h4 {
  padding: 0;
  margin-top: 0;
  margin-bottom: 30%;

  padding-top: 5%;
  color: black;
  font-family: "BMDOHYEON", sans-serif;
  font-size: 18px;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}
ul li {
  /* background-color: red; */
  list-style: none;
  float: left;
  /* width: 22%; */
  margin-right: 0.4em;
  margin-left: 0.3em;
  /* position: relative; */
  /* overflow: hidden; */
  text-align: center;
  cursor: pointer;
  width: 30%;
  height: 30%;
  border: 0px solid;
}
.check_img {
  filter: grayscale(100%);
}

.select_btn_container {
  width: 100%;
  display: flex;
  justify-content: center;
  padding-bottom: 10px;
  height: 4em;
}
.select_btn_container .btn-box {
  width: 45vw;
  height: 3em;
  max-width: 170px;
  transition: 0.25s cubic-bezier(0.25, 0, 0, 1);
}
.select_btn_container .btn-box:active {
  transform: translateY(5%);
}

.select_btn_container .btn-box .select_btn {
  display: flex;
  justify-content: center;
  align-items: center;
  font-family: "BMDOHYEON", sans-serif;
  font-size: 2em;
  padding-top: 0.15em;
  border-radius: 10px;
  background: #f3951b;
  color: white;
  transition: 0.25s cubic-bezier(0.25, 0, 0, 1);
  border-radius: 60% 20px 20px 60%;
}
.select_btn_container .btn-box .leaves-1,
.select_btn_container .btn-box .leaves-2,
.select_btn_container .btn-box .leaves-3 {
  position: absolute;
  z-index: -1;
  width: 5px;
  height: 5px;
  border-right: 40px solid #40aa57;
  border-bottom: 10px solid transparent;
  border-radius: 50%;
  transition: 0.25s cubic-bezier(0.25, 0, 0, 1);
  right: -20%;
}
.select_btn_container .btn-box .leaves-1 {
  transform: rotate(-30deg);
  top: 15%;
}
.select_btn_container .btn-box .leaves-2 {
  top: 38%;
}
.select_btn_container .btn-box .leaves-3 {
  transform: rotate(30deg);
  top: 61%;
}

@font-face {
  font-family: "BMDOHYEON";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_one@1.0/BMDOHYEON.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}
</style>

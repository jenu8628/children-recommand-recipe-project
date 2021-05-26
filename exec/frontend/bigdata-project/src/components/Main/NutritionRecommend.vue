<template>
  <div class="NutritionRecommend-container">
    <div
      class="recommend"
      v-for="(recipe, $index) in list"
      :key="$index"
      @scroll="current($index)"
    >
      <img
        id="recipeImg"
        class="recipeImg"
        :src="require(`@/assets/img/Main/${recipe.src}`)"
        alt=""
      />
      <div class="name">
        {{ recipe.name }}
      </div>
      <div class="direction">></div>
      <div class="time">{{ recipe.time }}min</div>
    </div>
    <infinite-loading
      spinner="bubbles"
      @infinite="infiniteHandler"
    ></infinite-loading>
  </div>
</template>

<script>
import "@/assets/css/Main/NutritionRecommend.css";

export default {
  data() {
    return {
      list: [],
      page: 0,
      // 레시피 과련 데이터
      recipes: [
        {
          name: "김치찌개 레시피",
          time: "15",
          // src : "https://an2-img.amz.wtchn.net/image/v1/watcha/image/upload/v1603708966/cmiozflbxxrh1jiekdrv.webp"
          src: "kimchisoup.jpg",
        },
        {
          name: "짜글이",
          time: "10",
          src: "jjagle.jpg",
        },
        {
          name: "부대찌개",
          time: "15",
          src: "budaesoup.jpg",
        },
        {
          name: "게맛살 볶음밥",
          time: "10",
          src: "krapFriedRice.jpg",
        },
        {
          name: "김치볶음밥",
          time: "10",
          src: "KimchiFriedRice.jpg",
        },
      ],
    };
  },
  methods: {
    // 무한스크롤 관련 함수
    infiniteHandler($state) {
      if (this.page > 0) {
        setTimeout(() => {
          if (this.page < this.recipes.length) {
            this.list.push(this.recipes[this.page]);
            this.page += 1;
            $state.loaded();
          } else {
            $state.complete();
          }
        }, 1000);
      } else {
        if (this.page < this.recipes.length) {
          this.list.push(this.recipes[this.page]);
          this.page += 1;
          $state.loaded();
        } else {
          $state.complete();
        }
      }
    },
  },
  mounted() {
    setTimeout(() => {}, 1000);
    this.infiniteHandler();
    // window.addEventListener('orientationchange', this.setLayout)
    // this.setLayout()
  },
  created() {
    // window.addEventListener("resize", this.setLayout)
  },
};
</script>

<style scoped>
</style>
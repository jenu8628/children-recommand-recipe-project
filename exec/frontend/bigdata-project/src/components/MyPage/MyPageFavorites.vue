<template>
  <div class="MyPageFavorites-container">
    <div class="MyPageFavorites-menu">즐겨찾기 목록</div>
    <div v-if="swap" class="full">
      <div
        class="favorite-box animate__animated"
        v-for="(favorite, index) in favorites"
        :key="index"
        :id="favorite.recipe_id"
      >
        <div class="favorite-bookmark">
          <i
            @click="recipeScrapCancle(favorite)"
            :class="'fas fa-bookmark ' + favorite.recipe_id"
            style="color: #40aa57"
          ></i>
        </div>
        <div class="result-card" @click="getRecipeDetail(favorite)">
          <div class="result-thumbnail">
            <div
              class="result-recipe_thumbnail"
              :style="
                'background-image : url(' + favorite.recipe_thumbnail + ')'
              "
            ></div>
          </div>
          <div class="result-header">
            <h2>{{ favorite.recipe_name }}</h2>
          </div>
          <div class="result-colorband"></div>
          <div class="result-desc-content">
            <span class="result_recipe_kcal"
              >열량 : {{ favorite.recipe_kcal }}(kcal)</span
            >
            <br />
            탄수화물 : {{ favorite.recipe_carbohydrate }}(g) <br />
            단백질 : {{ favorite.recipe_protein }}(g) <br />
            지방 : {{ favorite.recipe_fat }}(g) <br />
            나트륨 : {{ favorite.recipe_natrium }}(mg)
          </div>
          <div class="result-actions">
            <div class="result-recipe-rating">
              <img
                src="@/assets/img/star.png"
                class="recipe_rating_star"
                v-for="rating in favorite.recipe_rating"
                :key="rating"
              />
              <img
                src="@/assets/img/empty-star.png"
                class="recipe_rating_empty"
                v-for="empty in 5 - favorite.recipe_rating"
                :key="empty"
              />
            </div>
            <div class="result-recipe-difficulty">
              <img
                src="@/assets/img/Search/easy.png"
                alt="쉬움"
                v-if="favorite.recipe_difficulty == 1"
              />
              <img
                src="@/assets/img/Search/middle.png"
                alt="중간"
                v-if="
                  favorite.recipe_difficulty == 0 ||
                  favorite.recipe_difficulty == 2
                "
              />
              <img
                src="@/assets/img/Search/hard.png"
                alt="어려움"
                v-if="favorite.recipe_difficulty == 3"
              />
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="empty" v-else>
      <h2>좋아하는 메뉴를 추가하세요!</h2>
    </div>
    <MyPageTab @update="movepath" />
  </div>
</template>

<script>
import http from "@/api/http-common";
import "@/assets/css/MyPage/Favorites.css";
import MyPageTab from "./MyPageTab";

export default {
  data() {
    return {
      favorites: null,
    };
  },
  computed: {
    swap() {
      if (this.favorites) {
        return true;
      } else {
        return false;
      }
    },
  },
  components: {
    MyPageTab: MyPageTab,
  },
  methods: {
    getAllScrapRecipe() {
      http
        .get("/recipe/getAllScrapRecipe", {
          params: {
            scrap_parentId: localStorage.getItem("parent_id"),
          },
        })
        .then((res) => {
          if (res.data.length !== 0) {
            this.favorites = res.data;

            for (let i = 0; i < this.favorites.length; i++) {
              if (this.favorites[i].recipe_name.length > 7) {
                this.favorites[i].recipe_name =
                  this.favorites[i].recipe_name.slice(0, 6) + "..";
              }
            }
          } else {
            this.favorites = false;
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
    recipeScrapCancle(favorite) {
      const recipe = document.querySelector("#" + favorite.recipe_id);
      recipe.classList.add("animate__fadeOutLeft");

      setTimeout(() => {
        http
          .get("/recipe/cancelScrapToRecipe", {
            params: {
              scrap_parentId: localStorage.getItem("parent_id"),
              scrap_recipeId: favorite.recipe_id,
            },
          })
          .then(() => {
            recipe.classList.remove("animate__fadeOutLeft");
            this.getAllScrapRecipe();
          });
      }, 600);
    },
    getRecipeDetail(favorite) {
      localStorage.setItem("recipe_id", favorite.recipe_id);
      this.$router.push({
        name: "RecipeDetail",
        params: {
          props_recipeId: favorite.recipe_id,
        },
      });
    },
    movepath(event) {
      if (this.$route.name === event) {
        window.location.reload();
      } else {
        this.$router.push({ name: event });
      }
    },
  },
  mounted() {
    this.getAllScrapRecipe();
  },
};
</script>

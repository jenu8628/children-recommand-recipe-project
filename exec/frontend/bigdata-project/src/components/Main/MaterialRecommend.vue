<template>
  <div class="MaterialRecommend-container">
    <div class="recomm-container" v-if="recipes.length != 0">
      <div
        class="recommend"
        v-for="(recipe, $index) in recipes"
        :key="$index"
        @scroll="current($index)"
      >
        <div
          class="recipe-card-main"
          @click="getRecipeDetail(recipe.recipe_id)"
        >
          <div
            id="recipeImg"
            class="recipe-card-image"
            :style="{
              backgroundImage: 'url(' + recipe.recipe_thumbnail + ')',
            }"
          ></div>
          <p class="recipe-card-name">
            {{ recipe.recipe_name }}
          </p>
        </div>
        <div class="recipe-card-footer">
          <svg
            class="recipe__icon"
            xmlns="http://www.w3.org/2000/svg"
            xmlns:xlink="http://www.w3.org/1999/xlink"
            version="1.1"
            width="24"
            height="24"
            viewBox="0 0 24 24"
          >
            <path
              d="M15.5,21L14,8H16.23L15.1,3.46L16.84,3L18.09,8H22L20.5,21H15.5M5,11H10A3,3 0 0,1 13,14H2A3,3 0 0,1 5,11M13,18A3,3 0 0,1 10,21H5A3,3 0 0,1 2,18H13M3,15H8L9.5,16.5L11,15H12A1,1 0 0,1 13,16A1,1 0 0,1 12,17H3A1,1 0 0,1 2,16A1,1 0 0,1 3,15Z"
            />
          </svg>
          <h2 class="ingredients-title">영양성분</h2>
          <ul class="ingredients">
            <li class="ingredient">
              <p class="nutrition-left">
                탄수화물 : {{ recipe.recipe_carbohydrate }} g
              </p>
              <p class="nutrition-right">
                칼로리 : {{ recipe.recipe_kcal }} Kcal
              </p>
            </li>
            <li class="ingredient">
              <p class="nutrition-left">
                단백질 : {{ recipe.recipe_protein }} g
              </p>

              <p class="nutrition-right">
                나트륨 : {{ recipe.recipe_natrium }} mg
              </p>
            </li>
            <li class="ingredient">
              <p class="nutrition-left">지방 : {{ recipe.recipe_fat }} g</p>
              <p class="nutrition-right"></p>
            </li>
            <span></span>
          </ul>
          <button class="like-btn">
            <i
              v-if="loginTypeCheck"
              @click="likechange(recipe.recipe_id)"
              class="far fa-bookmark"
              id="like-btn-for-parent"
            ></i>
            <!-- 아이가 부모에게 채팅으로 레시피 보내기용 -->
            <i
              v-else
              class="fas fa-concierge-bell"
              id="like-btn-for-child"
              @click="sendMessageRecipe(recipe.recipe_id)"
            ></i>
          </button>
        </div>
      </div>
      <div v-if="checkModal" class="send-modal">
        <div class="send-recipe">
          <div>
            <p class="send-modal-content">먹고싶어요!</p>
          </div>
        </div>
      </div>
      <div class="scroll-btn scroll" @click="moveToTop">
        <i class="fa fa-angle-up" aria-hidden="true"></i>
      </div>
    </div>

    <div class="infinite-loading-container" v-else-if="recipes.length == 0">
      <infinite-loading
        spinner="bubbles"
        @infinite="infiniteHandler"
      ></infinite-loading>
    </div>
  </div>
</template>

<script>
import http from "@/api/http-common";
import "@/assets/css/Main/MaterialRecommend.css";
export default {
  data() {
    return {
      list: [],
      page: 0,
      recipes: [],
      like: false,
      loginCheck: false,
      loginTypeCheck: null,
      checkModal: false,
      checkModalMessage: "",
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
            // $state.complete();
          }
        }, 1000);
      } else {
        if (this.page < this.recipes.length) {
          this.list.push(this.recipes[this.page]);
          this.page += 1;
          $state.loaded();
        } else {
          // $state.complete();
        }
      }
    },
    // 추천 레시피 가져오기
    getAllRecipe() {
      var childId = localStorage.getItem("child_id");
      http
        .get("/recipe/getRecommnendationRecipes", {
          params: { child_id: childId },
        })
        .then((res) => {
          this.recipes = res.data;
          if (res.data.length == 0) {
            http
              .get("/recipe/getRecommendationRecipe", {
                params: { recommendationRecipe_childId: childId },
              })
              .then((res) => {
                this.recipes = res.data;
              });
          }
        });
    },
    getRecipeDetail(pic) {
      if (this.loginTypeCheck) {
        localStorage.setItem("recipe_id", pic);
        this.$router.push({
          name: "RecipeDetail",
          params: {
            props_recipeId: pic,
          },
        });
      }
    },
    moveToTop() {
      window.scrollTo({
        top:
          document.body.scrollHeight -
          document.querySelector(".MaterialRecommend-container").scrollHeight,
        left: 0,
        behavior: "smooth",
      });
    },
    // 레시피 즐겨찾기
    likechange(recipe_id) {
      this.like = !this.like;
      const icon = document.querySelector("#like-btn-for-parent");
      if (this.like) {
        icon.style.color = "yellow";
        this.recipeScrap(recipe_id);
      } else {
        icon.style.color = "white";
        icon.style.boxShadow = "";
        this.recipeScrapCancel(recipe_id);
      }
    },
    recipeScrap(recipe_id) {
      http.get("/recipe/scrapToRecipe", {
        params: {
          scrap_parentId: localStorage.getItem("parent_id"),
          scrap_recipeId: recipe_id,
        },
      });
    },
    recipeScrapCancel(recipe_id) {
      http.get("/recipe/cancelScrapToRecipe", {
        params: {
          scrap_parentId: localStorage.getItem("parent_id"),
          scrap_recipeId: recipe_id,
        },
      });
    },
    getFavorite(recipe_id) {
      http
        .get("/recipe/getAllScrapRecipe", {
          params: {
            scrap_parentId: localStorage.getItem("parent_id"),
          },
        })
        .then((res) => {
          const likeDisc = res.data.filter((data) => {
            return data.recipe_id.includes(recipe_id);
          });
          if (likeDisc.length !== 0) {
            this.like = true;
            const icon = document.querySelector(".fa-bookmark");
            icon.style.color = "f395#1b";
          }
        });
    },
    sendMessageRecipe(recipe_id) {
      var formData = new FormData();
      formData.append("message_senderId", localStorage.getItem("child_id"));
      formData.append("message_receiverId", localStorage.getItem("parent_id"));
      formData.append("message_content", "{!recipe}" + recipe_id);

      http.post("/message/sendMessage", formData).then(() => {
        this.checkModal = true;

        setTimeout(() => {
          this.checkModal = false;
        }, 800);
      });
    },
  },
  mounted() {
    setTimeout(() => {}, 1000);
    this.infiniteHandler();
    const token = localStorage.getItem("jwt");
    const childToken = localStorage.getItem("child_jwt");
    const loginTypeof = localStorage.getItem("loginType");
    if (token || childToken) {
      this.loginCheck = true;
      if (loginTypeof === "1") {
        this.loginTypeCheck = true;
      } else if (loginTypeof === "2") {
        this.loginTypeCheck = false;
      }
    }
    this.getFavorite();
  },
  created() {
    this.getAllRecipe();
  },
};
</script>

<style scoped></style>

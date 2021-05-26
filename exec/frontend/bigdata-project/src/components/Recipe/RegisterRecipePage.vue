<template>
  <div class="recipe-register-container">
    <!-- <div class="recipe-register page1">
      <div class="recipe-title-wrapper">
        <label for="recipe-title">메뉴명</label><br />
        <input
          type="text"
          id="recipe-title"
          placeholder="매뉴명을 입력해주세요"
          v-model="recipeTitle"
        /><br /><br />
      </div>
      <label for="recipe-method">조리방법:</label>
      <select name="recipe-method" id="recipe-method">
        <option value="끓이기">끓이기</option>
        <option value="굽기">굽기</option>
        <option value="튀기기">튀기기</option>
        <option value="삶기">삶기</option></select
      ><br /><br />

      <label for="recipe-kind">요리종류:</label>
      <select name="recipe-kind" id="recipe-kind">
        <option value="밥">밥</option>
        <option value="반찬">반찬</option>
        <option value="국&찌개">국&찌개</option>
        <option value="간식">간식</option></select
      ><br /><br />
    </div> -->

    <!-- 레시피 등록 페이지 1 -->
    <div class="recipe-register recipe-register-page1">
      <div class="recipe-form-group recipe-form-title">
        <h2>레시피 등록</h2>
      </div>
      <div class="recipe-form-group">
        <input
          type="text"
          name="trip_date"
          class="form-control"
          placeholder="메뉴명을 입력해주세요."
          id="req_date"
          required
        />
        <label for="req_date">메뉴명</label>
      </div>
      <br />

      <div class="recipe-form-group method-kind-form">
        <div class="recipe-method-kind recipe-method">
          <select class="form-control" required>
            <option value="" selected disabled>조리방법</option>
            <option value="끓이기">끓이기</option>
            <option value="굽기">굽기</option>
            <option value="튀기기">튀기기</option>
            <option value="삶기">삶기</option>
          </select>
          <label for="num_pax">조리방법</label>
        </div>
        <div class="recipe-method-kind recipe-kind">
          <select class="form-control" required>
            <option value="" selected disabled>요리종류</option>
            <option value="밥">밥</option>
            <option value="반찬">반찬</option>
            <option value="국&찌개">국&찌개</option>
            <option value="간식">간식</option>
          </select>
          <label for="num_pax">요리종류</label>
        </div>
      </div>

      <div class="recipe-form-group">
        <p>&#60; 재료 추가 &#62;</p>
      </div>
      <div class="recipe-form-group add-material-field" id="add-material-field">
        <div class="add-material" id="add-material">
          <div class="material material-name">
            <input
              type="text"
              name="first_name"
              class="form-control"
              required
              placeholder="재료명"
              id="fname"
            />
            <label for="fname">재료명</label>
          </div>
          <div class="material material-type">
            <select name="anglers" class="form-control" id="num_pax" required>
              <option value="" selected disabled>계량기준</option>
              <option value="1/4">1/4</option>
              <option value="1/2">1/2</option>
              <option value="3/4">3/4</option>
              <option value="1/3">1/3</option>
              <option value="2/3">2/3</option>
              <option value="1">1</option>
              <option value="2">2</option>
              <option value="3">3</option>
            </select>
            <label for="num_pax">계량기준</label>
          </div>
          <div class="material material-volume">
            <select name="anglers" class="form-control" id="num_pax" required>
              <option value="작은술">작은술(5ml)</option>
              <option value="큰술">큰술(15ml)</option>
              <option value="컵">컵(200ml 기준)</option>
              <option value="">근(600g)</option>
            </select>
            <label for="num_pax">계량</label>
          </div>
        </div>
      </div>
      <button id="addBtn" @click="addMaterialDiv">+</button><br />
      <button type="submit">Continue</button>
    </div>

    <!-- 레시피 등록 페이지 2 -->
    <div class="recipe-register recipe-register-page2">
      <div class="wrapper-preview">
        <div class="recipe-upload-row">
          <div
            class="recipe-upload-item"
            :data-value="recipeProcess_image_current"
            draggable="true"
            :style="{
              backgroundImage: 'url(' + recipeProcess_image_current + ')',
            }"
            @click="showModal"
          >
            <div class="recipe-upload-btn" @click="showModal">
              <span>+</span>
            </div>
          </div>
          <div
            class="recipe-upload-item"
            :data-value="recipeProcess_image_current"
            draggable="true"
            :style="{
              backgroundImage: 'url(' + recipeProcess_image_current + ')',
            }"
            @click="showModal"
          >
            <div class="recipe-upload-btn" @click="showModal">
              <span>+</span>
            </div>
          </div>
          <div
            class="recipe-upload-item"
            :data-value="recipeProcess_image_current"
            draggable="true"
            :style="{
              backgroundImage: 'url(' + recipeProcess_image_current + ')',
            }"
            @click="showModal"
          >
            <div class="recipe-upload-btn" @click="showModal">
              <span>+</span>
            </div>
          </div>
        </div>
        <div class="recipe-upload-row">
          <div
            class="recipe-upload-item"
            :data-value="recipeProcess_image_current"
            draggable="true"
            :style="{
              backgroundImage: 'url(' + recipeProcess_image_current + ')',
            }"
            @click="showModal"
          >
            <div class="recipe-upload-btn" @click="showModal">
              <span>+</span>
            </div>
          </div>
          <div
            class="recipe-upload-item"
            :data-value="recipeProcess_image_current"
            draggable="true"
            :style="{
              backgroundImage: 'url(' + recipeProcess_image_current + ')',
            }"
            @click="showModal"
          >
            <div class="recipe-upload-btn" @click="showModal">
              <span>+</span>
            </div>
          </div>
          <div
            class="recipe-upload-item"
            :data-value="recipeProcess_image_current"
            draggable="true"
            :style="{
              backgroundImage: 'url(' + recipeProcess_image_current + ')',
            }"
            @click="showModal"
          >
            <div class="recipe-upload-btn" @click="showModal">
              <span>+</span>
            </div>
          </div>
        </div>
        <div class="recipe-upload-row">
          <div
            class="recipe-upload-item"
            :data-value="recipeProcess_image_current"
            draggable="true"
            :style="{
              backgroundImage: 'url(' + recipeProcess_image_current + ')',
            }"
            @click="showModal"
          >
            <div class="recipe-upload-btn" @click="showModal">
              <span>+</span>
            </div>
          </div>
          <div
            class="recipe-upload-item"
            :data-value="recipeProcess_image_current"
            draggable="true"
            :style="{
              backgroundImage: 'url(' + recipeProcess_image_current + ')',
            }"
            @click="showModal"
          >
            <div class="recipe-upload-btn" @click="showModal">
              <span>+</span>
            </div>
          </div>
          <div
            class="recipe-upload-item"
            :data-value="recipeProcess_image_current"
            draggable="true"
            :style="{
              backgroundImage: 'url(' + recipeProcess_image_current + ')',
            }"
            @click="showModal"
          >
            <div class="recipe-upload-btn" @click="showModal">
              <span>+</span>
            </div>
          </div>
        </div>
        <div class="recipe-upload-row">
          <div
            class="recipe-upload-item"
            :data-value="recipeProcess_image_current"
            draggable="true"
            :style="{
              backgroundImage: 'url(' + recipeProcess_image_current + ')',
            }"
            @click="showModal"
          >
            <div class="recipe-upload-btn" @click="showModal">
              <span>+</span>
            </div>
          </div>
          <div
            class="recipe-upload-item"
            :data-value="recipeProcess_image_current"
            draggable="true"
            :style="{
              backgroundImage: 'url(' + recipeProcess_image_current + ')',
            }"
            @click="showModal"
          >
            <div class="recipe-upload-btn" @click="showModal">
              <span>+</span>
            </div>
          </div>
          <div
            class="recipe-upload-item"
            :data-value="recipeProcess_image_current"
            draggable="true"
            :style="{
              backgroundImage: 'url(' + recipeProcess_image_current + ')',
            }"
            @click="showModal"
          >
            <div class="recipe-upload-btn" @click="showModal">
              <span>+</span>
            </div>
          </div>
        </div>
        <!--------------------여기까지 사진 업로드 블록------------------------>

        <!-------------- Modal 창 ------------------>
        <Modal v-if="showModal_register" @close="showModal_register = false">
          <div slot="header" class="wrap-upload-header">
            <h2>요리과정 등록</h2>
            <i
              class="fas fa-times closeModalBtn"
              @click="showModal_register = false"
            ></i>
          </div>

          <div slot="body" class="wrap-upload-body">
            <div class="upload-picture">
              <!-- <p class="upload-picture-p">사진 미리보기</p> css 같이 지우기.-->
              <image-uploader
                ref="recipeProcess_image_current"
                :preview="true"
                :className="['fileinput', { 'fileinput--loaded': hasImage }]"
                capture="environment"
                :debug="1"
                doNotResize="gif"
                :autoRotate="true"
                outputFormat="blob"
                @input="setImage"
                :maxWidth="512"
                :maxHeight="512"
              >
              </image-uploader>
              <label for="fileInput" slot="upload-label">
                <span class="upload_caption">{{
                  hasImage ? "사진 변경" : "사진 선택"
                }}</span>
              </label>
            </div>
            <div class="desc_of_recipe">
              <textarea
                type="textarea"
                id="gallery_desc"
                placeholder="요리과정을 작성해 주세요."
                v-model="recipeProcess_current"
              ></textarea>
            </div>
          </div>

          <div slot="footer" class="wrap-upload-footer">
            <div class="registerRecipeItem" @click="makeRecipeItem">
              <span>등록하기</span>
            </div>
            <div
              class="closeRegisterRecipeBtn"
              @click="showModal_register = false"
            >
              <span>나가기</span>
            </div>
          </div>
        </Modal>
      </div>
      <div class="recipe-upload-form"></div>
    </div>
  </div>
</template>

<script>
import "../../assets/css/Recipe/RegisterRecipePage.css";
import Modal from "../Recipe/Modal";
import ImageUploader from "vue-image-upload-resize";

export default {
  data() {
    return {
      recipeTitle: "",
      recipe_img_list: [], // 업로드된 이미지 리스트
      recipe_desc_list: [], // 업로드된 요리과정 설명 리스트
      recipeProcess_image_current: "", // 마지막으로 업로드한 만드는법 이미지
      recipeProcess_current: "", // 마지막으로 업로드한 만드는법
      showModal_register: false,
      hasImage: false,
    };
  },
  components: {
    Modal,
    ImageUploader,
  },
  methods: {
    addMaterialDiv: function () {
      var div = document.getElementById("add-material").cloneNode(true);
      var label = div.querySelectorAll(".material label");
      for (var i = 0; i < label.length; i++) {
        div.children[i].removeChild(label[i]);
      }
      document.getElementById("add-material-field").appendChild(div);
    },
    showModal: function () {
      this.showModal_register = true;
    },
    setImage: function (output) {
      this.hasImage = true;
      this.recipeProcess_image_current = output;
    },
    makeRecipeItem: function () {},
  },
  mounted() {},
  created() {},
};
</script>

<style>
.recipe-register-container .recipe-register-page1 .recipe-title-wrapper {
  display: inline-block;
}
.recipe-register-container .recipe-register-page1 .recipe-title-wrapper label {
  float: left;
}
</style>
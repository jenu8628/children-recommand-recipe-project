<template>
  <div class="TodayTip-container">
    <div class="box">
      <div class="close">
        <i class="fas fa-times" @click="close"></i>
      </div>
      <div class="img-box" id="imagePreview"></div>
      <div class="name">{{ tip.name }}</div>

      <div class="button">
        <button @click="recommend">
          {{ tip.name }}<br />
          추천 레시피
        </button>
        <div class="leaves-1"></div>
        <div class="leaves-2"></div>
        <div class="leaves-3"></div>
      </div>
      <div class="today-stop">
        <i class="fas fa-check" @click="closeModal">오늘하루 그만보기</i>
      </div>
    </div>
  </div>
</template>

<script>
import "@/assets/css/Main/TodayTip.css";
import http from "@/api/http-common";

export default {
  data() {
    return {
      tip: {
        img: "spinach.jpg",
        name: "시금치",
        detail:
          "시금치의 원산지는 서남아시아 지역으로 중국을 통해 우리나라로 15세기 무렵 들어온 것으로 보입니다. 전 세계로 널리 퍼질 수 있었던 것은 추위도 잘 이겨내는 특성 덕분이라고 하네요. 현재는 전국적으로 재배되고 있지만 경기, 경남, 전남 쪽에서 전체의 70% 이상이 생산되고 있다고 합니다. 시금치가 유명한 곳은 남해, 포항, 비금도산인데요. 한겨울 해풀을 맞으며 심한 일교차와 날씨를 견디느라 땅바닥에 붙어 옆으로 퍼지면서 자라서 길이가 짧은것이 특징입니다. 바닷가 모래가 많은 곳에 자란 시금치는 당도가 높고 뿌리가 붉으며, 잎과 줄기가 두꺼워 신선도가 오래 유지됩니다.",
      },
      allTip: "",
      tipId: "",
      img_url: "",
      modalClose: "",
    };
  },
  methods: {
    close() {
      this.$emit("update");
    },
    recommend() {
      localStorage.setItem("recipe_name", this.tip.name);
      this.$router.push({ name: "Search" });
    },

    getAllTip() {
      http.get("admin/getAllTip").then((res) => {
        this.allTip = res.data;
        this.getTipInfo();
      });
    },
    getTipInfo() {
      const ramdomindex = Math.floor(Math.random() * this.allTip.length);

      this.tipId = this.allTip[ramdomindex].tip_id;

      http
        .get("admin/getTipInfo", {
          params: {
            tip_id: this.tipId,
          },
        })
        .then((res) => {
          let preview = document.querySelector("#imagePreview");
          this.img_url = "data:image/jpeg;base64," + res.data.tip_media;
          preview.style.backgroundImage = "url('" + this.img_url + "')";
          this.tip.name = res.data.tip_title;
          this.tip.detail = res.data.tip_content;
        });
    },
    closeModal() {
      localStorage.setItem("modal_close", this.modalClose);
    },
  },
  mounted() {
    this.getAllTip();
  },
};
</script>

<style>
</style>
<template>
  <div class="todaytip-container">
    <div id="todaytipadd">
      <h3>팁 추가</h3>
      <div class="tip-warp">
        <div class="content__line">
          <span class="header">팁 제목</span>
          <span>
            <input
              type="text"
              class="tip_input"
              value="<? echo $content ?>"
              id="inputTitle"
              v-model="tipTitle"
          /></span>
        </div>
        <div class="content__line">
          <span class="header">이미지 업로드</span>
          <span
            ><input
              type="file"
              ref="tipMedia"
              accept=".png, .jpg, .jpeg"
              @change="uploadTip"
          /></span>
        </div>
        <div class="content__line">
          <span class="header">팁 내용</span>
          <span>
            <input
              type="text"
              value="<? echo $content ?>"
              id="content"
              class="tip_text"
              v-model="tipContent"
          /></span>
        </div>

        <div class="button__add">
          <button @click="addTip"><i class="fas fa-plus"></i></button>
        </div>
      </div>
    </div>
    <div class="tips">
      <div><h3>팁 종류</h3></div>
      <div></div>
      <div></div>
      <div></div>
      <div v-for="(item, idx) in allTip" :key="idx">
        <div @click="getTipInfo(item.tip_id)"># {{ item.tip_id }}</div>
      </div>
    </div>
    <div id="tipinfo" v-if="infoTip">
      <h3>팁 상세정보</h3>
      <div @click="removeTip">
        <div id="imagepreview" style=""></div>
        팁 제목:{{ tipInfo.tip_title }}
        <br />
        팁 내용:{{ tipInfo.tip_content }}
        <!-- 팁 사진:{{img_url}} -->
      </div>
    </div>

    <AdminTab @update="movepath" />
  </div>
</template>

<script>
import http from "@/api/http-common";
import AdminTab from "./AdminTab";
export default {
  components: {
    AdminTab: AdminTab,
  },
  data() {
    return {
      tipContent: "",
      tipMedia: "",
      tipTitle: "",
      allTip: "",
      tipInfo: "",
      infoTip: false,
      tipImage: "",
      img_url: "",
    };
  },
  methods: {
    uploadTip(e) {
      this.tipMedia = e.target.files[0];
    },

    addTip() {
      var formData = new FormData();
      formData.append("tip_content", this.tipContent);
      formData.append("tip_media", this.tipMedia);
      formData.append("tip_title", this.tipTitle);
      http
        .post("admin/addTip", formData)
        .then(() => {
          this.getAllTip();
        })
        .catch((err) => {
          console.log(err);
        });
    },
    getAllTip() {
      http.get("admin/getAllTip").then((res) => {
        this.allTip = res.data;
      });
    },
    getTipInfo(event) {
      http
        .get("/admin/getTipInfo", {
          params: {
            tip_id: event,
          },
        })
        .then((res) => {
          this.tipInfo = res.data;
          this.infoTip = true;
          let preview = document.querySelector("#imagepreview");
          this.img_url = "data:image/jpeg;base64," + res.data.tip_media;
          preview.style.backgroundImage = "url(" + `${this.img_url}` + ")";
        });
    },
    removeTip() {
      http
        .delete("/admin/removeTip", {
          params: {
            tip_id: this.tipInfo.tip_id,
          },
        })
        .then((res) => {
          alert("삭제하시겠습니까?");
          this.tipInfo = res.data;
          this.getAllTip();
          location.reload();
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
    this.getAllTip();
  },
};
</script>


<style>
.todaytip-container {
  overflow: hidden;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  width: 100vw;
  height: 100vh;
  align-items: center;
  font-family: "BMDOHYEON";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_one@1.0/BMDOHYEON.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}
.todaytip-container #todaytipadd {
  width: 100%;
  height: 40%;
  margin-top: 20%;
  padding-left: 5%;
}

.tips {
  display: grid;
  grid-template-columns: 1fr 1fr 1fr 1fr;
  margin-top: 5%;
  width: 100%;
  height: 10%;
  padding-left: 5%;
  overflow: scroll;
}

.todaytip-container #tipinfo {
  margin-top: 5%;
  width: 100%;
  height: 30%;
  padding-left: 5%;
  padding-bottom: 5rem;

  overflow: scroll;
}

.tip-warp .content__line .header {
  display: inline-block;
  width: 100px;
  text-align: center;
  vertical-align: top;
  padding-top: 5px;
}
.tip-warp .content__line .tip_input {
  width: 250px;
  border: 1px solid #ccc;
  background-color: #f4f4f4;
  border-radius: 3px;
  height: 22px;
  line-height: 22px;
}
.tip-warp .content__line .tip_text {
  width: 250px;
  border: 1px solid #ccc;
  background-color: #f4f4f4;
  border-radius: 3px;
  height: 200px;
  line-height: 200px;
}

.button__add {
  display: flex;
  padding-left: 30%;
}

#imagepreview {
  background-size: cover;
  width: 200px;
  height: 200px;
  border: 1px black solid;
  z-index: 3;
}
</style>
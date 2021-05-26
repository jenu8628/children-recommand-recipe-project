<template>
  <div class="Chat">
    <div class="Chat-container" v-cloak>
      <div class="Chat-panel">
        <!-- Header -->
        <div class="Chat-header">
          <div class="Chat-arrow">
            <div
              class="Chat-header-icon"
              v-if="byParent"
              @click="locationBack"
            ></div>
          </div>
          <div class="Chat-header-content">
            <div class="Chat-header-body">
              <h4 class="Chat-media-heading">{{ message_receiverName }}</h4>
            </div>
          </div>
          <div class="Chat-empty"></div>
        </div>
      </div>
      <div class="Chat-message-container">
        <div class="Chat-message-content">
          <div class="Chat-list-group" v-if="messageList.length != 0">
            <div
              class="Chat-list-group-container"
              v-for="message in messageList"
              :key="message.message_id"
            >
              <!-- <div class="Chat-list-flag">{{ message.message_flag }}</div> -->
              <div
                :class="
                  'Chat-list-group-item animate__animated ' +
                  message.bubble_style +
                  ' ' +
                  message.recipe_bubble_style
                "
              >
                <div
                  v-if="message.message_recipeId == null"
                  class="Chat-list-content"
                >
                  {{ message.message_content }}
                </div>
                <div v-else class="Chat-list-recipe">
                  <div
                    class="Chat-recipe"
                    @click="gotoRecipeDetail"
                    :id="message.message_recipeId"
                  >
                    <img
                      :src="message.message_recipeImg"
                      alt="레시피 이미지"
                      :id="message.message_recipeId"
                    />

                    <div
                      class="Chat-recipe-name"
                      :id="message.message_recipeId"
                    >
                      <br />
                      " {{ message.message_recipeName }} "
                      <br />
                      <br />
                      먹고싶어요!
                    </div>
                  </div>
                </div>
                <!-- <div class="bubble_corner"></div> -->
                <div class="corner">
                  <i class="fas fa-caret-right"></i>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="Chat-message-sender">
          <input
            type="text"
            placeholder="Message"
            @keypress.enter="sendMessage"
            v-model="message_content"
            id="inputMessage"
          />
          <div class="Chat-send-icon" @click="sendMessage"></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import "@/assets/css/Chat/Chat.css";
import http from "@/api/http-common.js";
import Stomp from "webstomp-client";
import SockJS from "sockjs-client";

export default {
  name: "Chat",
  data() {
    return {
      messageRoom_hashId: "",
      messageRoom: {},
      message_receiverId: "",
      message_senderId: "",
      message_content: "",
      messageList: [],
      connected: false,
      message_receiverName: "",
      recipe_img: "",
      byParent: false,
    };
  },
  created() {
    this.getMessageRoomHashId();

    this.loginType = localStorage.getItem("loginType");

    if (localStorage.getItem("loginType") == 1) {
      // 부모가 로그인 상태인 경우
      this.message_senderId = localStorage.getItem("parent_id");
      this.message_receiverId = localStorage.getItem("child_id");
      this.byParent = true;
    } else if (localStorage.getItem("loginType") == 2) {
      // 아이가 로그인 상태인 경우
      this.message_senderId = localStorage.getItem("child_id");
      this.message_receiverId = localStorage.getItem("parent_id");
      // this.message_receiverId = "parent-1";
    }

    this.connect();

    // 읽음 여부 업데이트
  },
  mounted() {
    const input = document.querySelector("#inputMessage");
    input.focus();

    setTimeout(() => {
      this.gotobottom();
    }, 200);
  },
  methods: {
    connect() {
      this.socket = new SockJS(
        "http://j4b205.p.ssafy.io:7080/formychild/ws-stomp"
      );
      // this.socket = new SockJS("http://localhost:7080/formychild/ws-stomp");
      this.stompClient = Stomp.over(this.socket);

      this.stompClient.debug = () => {};

      this.stompClient.connect(
        {},
        (frame) => {
          this.connected = true;
          console.log("소켓 연결 성공", frame);

          this.stompClient.subscribe(
            "/sub/" + this.messageRoom_hashId,
            (res) => {
              let resData = JSON.parse(res.body);

              let m = {
                message_id: resData.message_id,
                message_senderId: resData.message_senderId,
                message_receiverId: resData.message_receiverId,
                message_content: resData.message_content,
                message_sendDate: resData.message_sendDate,
                message_flag: resData.message_flag,
                message_recipeId: resData.message_recipeId,
                message_messageRoomHashId: resData.message_messageRoomHashId,
                bubble_style:
                  resData.message_senderId == this.message_senderId
                    ? "Chat-bubble-right animate__bounceInRight"
                    : "Chat-bubble-left animate__bounceInLeft",
                recipe_bubble_style:
                  resData.message_recipeId == null
                    ? "Chat-none-recipe"
                    : "Chat-exist-recipe",
              };

              this.messageList.push(m);

              setTimeout(() => {
                this.gotobottom();
              }, 100);
            }
          );
        },
        (err) => {
          console.log("소켓 연결 실패", err);
          this.connected = false;
        }
      );
    },
    sendMessage() {
      if (this.message_content == "") {
        alert("메세지를 입력하세요");
        return;
      }

      if (this.stompClient && this.stompClient.connected) {
        const msg = {
          message_messageRoomHashId: this.messageRoom_hashId,
          message_senderId: this.message_senderId,
          message_receiverId: this.message_receiverId,
          message_content: this.message_content,
        };

        this.stompClient.send("/pub/message/send", JSON.stringify(msg));
        this.message_content = "";
      }
    },
    findRoom() {
      http.get("/message/findMessageRoomById/" + this.messageRoom_hashId).then(
        (res) => {
          this.messageRoom = res.data;

          this.updateReadFlag();
        },
        (err) => {
          console.log(err);
        }
      );
    },
    getMessages() {
      http.get("/message/getMessages/" + this.messageRoom_hashId).then(
        (res) => {
          var resData = res.data;

          for (let i = 0; i < resData.length; i++) {
            let m = {
              message_id: resData[i].message_id,
              message_senderId: resData[i].message_senderId,
              message_receiverId: resData[i].message_receiverId,
              message_content: resData[i].message_content,
              message_sendDate: resData[i].message_sendDate,
              message_flag: resData[i].message_flag,
              message_recipeId: resData[i].message_recipeId,
              message_recipeImg: "",
              message_recipeName: "",
              message_messageRoomHashId: resData[i].message_messageRoomHashId,
              bubble_style:
                resData[i].message_senderId == this.message_senderId
                  ? "Chat-bubble-right"
                  : "Chat-bubble-left",
              recipe_bubble_style: "",
            };
            this.messageList.push(m);
          }
          this.getRecipeContent();
        },
        (err) => {
          console.log(err);
        }
      );
    },
    getMessageRoomHashId() {
      http
        .get("/message/getMessageRoomHashId", {
          params: {
            messageRoom_parentId: localStorage.getItem("parent_id"),
            messageRoom_childId: localStorage.getItem("child_id"),
          },
        })
        .then(
          (res) => {
            this.messageRoom_hashId = res.data;

            this.findRoom();

            if (localStorage.getItem("loginType") == 1) {
              this.getMyChildName();
            } else if (localStorage.getItem("loginType") == 2) {
              this.message_receiverName = "부모님";
            }
          },
          (err) => {
            console.log(err);
          }
        );
    },
    getMyChildName() {
      http
        .get("/child/getMyChild", {
          params: {
            child_id: localStorage.getItem("child_id"),
          },
        })
        .then(
          (res) => {
            this.message_receiverName = res.data.child_name;
          },
          (err) => {
            console.log(err);
          }
        );
    },
    locationBack() {
      this.$router.push({
        name: "ChatList",
      });
    },
    gotobottom() {
      const message_list = document.querySelector(".Chat-message-content");
      var location = message_list.scrollHeight + 89;
      message_list.scrollTo({ top: location, behavior: "smooth" });
    },
    updateReadFlag() {
      let today = new Date();
      let year = today.getFullYear(); // 년도
      let month = today.getMonth() + 1; // 월
      let date = today.getDate(); // 날짜
      let hours = today.getHours(); // 시
      let minutes = today.getMinutes(); // 분
      let seconds = today.getSeconds(); // 초

      let sendDate =
        year +
        "-" +
        ("00" + month.toString()).slice(-2) +
        "-" +
        ("00" + date.toString()).slice(-2) +
        " " +
        hours +
        ":" +
        minutes +
        ":" +
        ("00" + seconds.toString()).slice(-2);

      sendDate;

      var formData = new FormData();
      formData.append("message_messageRoomHashId", this.messageRoom_hashId);
      formData.append("message_receiverId", this.message_senderId);
      formData.append("message_sendDate", sendDate);

      http.put("/message/readMessage", formData).then(
        () => {
          this.getMessages();
        },
        (err) => {
          console.log(err);
        }
      );
    },
    getRecipeImg() {
      for (let i = 0; i < this.messageList.length; i++) {
        if (this.messageList[i].message_recipeId != null) {
          http
            .get("/recipe/getRecipe/" + this.messageList[i].message_recipeId)
            .then((res) => {
              this.messageList[i].message_recipeImg = res.data.recipe_thumbnail;
              this.messageList[i].message_recipeName = res.data.recipe_name;
            });
        }
      }
    },
    gotoRecipeDetail(e) {
      localStorage.setItem("recipe_id", e.target.id);
      this.$router.push({
        name: "RecipeDetail",
      });
    },
    getRecipeContent() {
      for (let i = 0; i < this.messageList.length; i++) {
        let content = this.messageList[i].message_content;

        if (content.slice(0, 9) == "{!recipe}") {
          this.messageList[i].message_recipeId = this.messageList[
            i
          ].message_content.slice(
            9,
            this.messageList[i].message_content.length
          );

          this.messageList[i].recipe_bubble_style = "Chat-exist-recipe";
        }
      }

      this.getRecipeImg();
    },
  },
  components: {},
};
</script>

<style></style>

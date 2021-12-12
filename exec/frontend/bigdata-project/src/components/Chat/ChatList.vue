<template>
  <div class="ChatList">
    <div class="ChatList-container" v-cloak>
      <div class="ChatList-panel">
        <!-- Header -->
        <div class="ChatList-header">
          <div class="ChatLis-empty"></div>
          <div class="ChatList-header-content">
            <img
              src="@/assets/img/Chat/chat.png"
              alt="chatting"
              class="ChatList-Img"
              style="width: 2.5em; height: 2.5em"
            />
          </div>
          <div class="ChatList-empty"></div>
        </div>
      </div>
      <div class="ChatList-messageRoom-container">
        <div class="ChatList-list-group">
          <div
            class="ChatList-list-group-item"
            v-for="(room, idx) in messageRooms"
            :key="room.messageRoom_id"
            @click="enterRoom(idx)"
          >
            <div class="ChatList-profile">
              <div
                id="ChatList-image"
                :style="
                  'background-image: url(' + children[idx].child_profile + ');'
                "
              ></div>
            </div>
            <div class="ChatList-name">
              {{ children[idx].child_name }} 채팅방
            </div>
            <div class="ChatList-unread">
              <div class="ChatList-num">{{ unreadList[idx] }}</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import "@/assets/css/Chat/ChatList.css";
import http from "@/api/http-common.js";
import router from "../../router";

export default {
  data() {
    return {
      messageRooms: [],
      children: [],
      unreadList: [],
    };
  },
  created() {
    this.findMyChildren();
    this.findMessageRoom();
  },
  methods: {
    findMessageRoom() {
      http
        .get("/message/getMessageRoomMyChildren", {
          params: {
            messageRoom_parentId: localStorage.getItem("parent_id"),
          },
        })
        .then(
          (res) => {
            this.messageRooms = res.data;
            console.log(res.data)
            for (let i = 0; i < this.messageRooms.length; i++) {
              this.getUnread(this.messageRooms[i].messageRoom_hashId);
            }
          },
          (err) => {
            console.log(err);
          }
        );
    },
    enterRoom(idx) {
      console.log(idx)
      // var idx = e.target.id;
      var child_id = this.children[idx].child_id;
      localStorage.setItem("child_id", child_id);

      router.push({
        name: "Chat",
      });
    },
    findMyChildren() {
      http
        .get("/child/getMyChildren", {
          params: {
            child_parentId: localStorage.getItem("parent_id"),
          },
        })
        .then(
          (res) => {
            this.children = res.data;
            for (let i = 0; i < this.children.length; i++) {
              this.children[i].child_profile =
                "data:image/jpeg;base64," + this.children[i].child_profile;
            }
          },
          (err) => {
            console.log(err);
          }
        );
    },
    getUnread(hash_id) {
      http
        .get("/message/getUnreadMessage", {
          params: {
            message_messageRoomHashId: hash_id,
            message_receiverId: localStorage.getItem("parent_id"),
          },
        })
        .then(
          (res) => {
            this.unreadList.push(res.data);
          },
          (err) => {
            console.log(err);
          }
        );
    },
  },
};
</script>

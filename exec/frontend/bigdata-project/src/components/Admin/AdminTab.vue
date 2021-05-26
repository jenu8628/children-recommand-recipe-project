<template>
  <div class="admin-tab-container">
    <div @click="move('Admin')">보호자목록</div>
    <div @click="move('AdminChild')">자녀목록</div>
    <div @click="move('TodayTip')">오늘의팁</div>
  </div>
</template>

<script>
export default {
  data() {
    return {};
  },
  methods: {
    move(event) {
      this.$emit("update", event);
    },
  },
};
</script>

<style>
.admin-tab-container {
  display: flex;
  border-top: gray solid 1px;
  align-items: center;
  justify-content: space-evenly;
  position: fixed;
  background: white;
  bottom: 0;
  height: 10vh;
  width: 100vw;
  z-index: 10;
  font-size: 18px;
}
</style>
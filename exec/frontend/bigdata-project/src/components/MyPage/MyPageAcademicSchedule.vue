<template>
  <div class="mypageacademicschedule">
    <Calrender @update="calender" />

    <div id="schedule">
      <div>학사일정</div>

      <div id="schedule-table"></div>
    </div>
  </div>
</template>

<script>
import Calrender from "./Calrender.vue";
import axios from "axios";

export default {
  data() {
    return {
      date: "",
      school: "늘봄초등학교",
      schoolFeeding: null,
      schoolFeedingDetail: null,
      currentSchoolFeedingId: "",
    };
  },
  components: { Calrender },
  methods: {
    calender(event) {
      this.date = event.year + event.month + event.day;
      axios
        .get(
          `http://127.0.0.1:8000/myapp/schoolfeeding/${this.date}/${this.school}/`
        )
        .then((res) => {
          this.schoolFeeding = res.data[0];
          this.currentSchoolFeedingId = res.data[0].schoolfeeding_id;
          axios
            .get(
              `http://127.0.0.1:8000/myapp/schoolfeedingdetail/${this.currentSchoolFeedingId}/`
            )
            .then((res) => {
              this.schoolFeedingDetail = res.data[0];
            })
            .catch((err) => {
              console.error(err);
            });
        })
        .catch(() => {
          this.schoolFeeding = null;
          this.schoolFeedingDetail = null;
        });
    },
  },
  mounted() {},
};
</script>

<style>
.mypageacademicschedule {
  display: flex;
  width: 100vw;
  height: 100vh;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}

/* .mypageacademicschedule #calender {
  display: flex;
  width: 80%;
} */
.mypageacademicschedule #schedule {
  display: flex;
  width: 100%;
  height: 10%;
  background-color: coral;
  align-items: center;
  justify-content: space-around;
  margin-top: 15%;
}
.mypageacademicschedule #schedule #schedule-table {
  overflow: scroll;
  display: flex;
  flex-direction: column;
  height: 20%;
}
</style>
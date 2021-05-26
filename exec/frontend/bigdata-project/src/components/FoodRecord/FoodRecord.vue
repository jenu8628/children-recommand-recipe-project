<template>
  <div class="FoodRecord-container">
    <div class="record-title">{{ childName }}의 식단<br />기록 달력</div>
    <section class="calrender-box">
      <!-- <div class="calrender-container"> -->
      <div id="calrender-top">
        <button @click="calendarData(-1)">
          <i class="fas fa-chevron-left"></i>
        </button>
        {{ year }}년 {{ month }}월
        <button @click="calendarData(1)">
          <i class="fas fa-chevron-right"></i>
        </button>
      </div>

      <table id="calrender-table">
        <thead>
          <th v-for="day in days" :key="day">{{ day }}</th>
        </thead>
        <tbody>
          <tr v-for="(date, idx) in dates" :key="idx">
            <td
              style="padding: 0"
              class="cursor"
              v-for="(day, secondIdx) in date"
              :key="secondIdx"
              :class="{
                'has-text-info-dark': idx === 0 && day >= lastMonthStart,
                'has-text-danger':
                  dates.length - 1 === idx && nextMonthStart > day,
                'has-text-primary':
                  day === today &&
                  month === currentMonth &&
                  year === currentYear,
              }"
              @click="daily(day)"
            >
              <div
                class="day"
                :style="{
                  backgroundImage: 'url(' + backgroundImg[day - 1] + ')',
                }"
              >
                {{ day }}
              </div>
            </td>
          </tr>
        </tbody>
      </table>
      <!-- </div> -->
    </section>
    <MyPageTab v-if="loginType === '1'" @update="movepath" />
  </div>
</template>

<script>
import http from "@/api/http-common";
import MyPageTab from "../MyPage/MyPageTab";

export default {
  components: {
    MyPageTab: MyPageTab,
  },
  data() {
    return {
      days: ["SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"],
      dates: [],
      currentYear: 0,
      currentMonth: 0,
      year: 0,
      month: 0,
      lastMonthStart: 0,
      nextMonthStart: 0,
      currentMonthEnd: 0,
      today: 0,
      backgroundImg: [],
      foodRecordList: null,
      loginType: "",
      childName: "",
    };
  },
  created() {
    // 데이터에 접근이 가능한 첫 번째 라이프 사이클
    // setTimeout(()=>{
    //   },500)
    const date = new Date();
    this.currentYear = date.getFullYear(); // 이하 현재 년, 월 데이터 저장해놓기
    this.currentMonth = date.getMonth() + 1;
    this.year = this.currentYear;
    this.month = this.currentMonth;
    this.currentMonthEnd = Math.max.apply(
      null,
      this.dates[this.dates.length - 1]
    );
    this.today = date.getDate(); // 오늘 날짜
    this.calendarData();
    this.getMyChildName();
    // this.daily(this.today)
  },
  computed: {},
  mounted() {
    this.loginType = localStorage.getItem("loginType");
  },
  methods: {
    movepath(event) {
      if (this.$route.name === event) {
        window.location.reload();
      } else {
        this.$router.push({ name: event });
      }
    },
    getMyChildName() {
      http
        .get("/child/getMyChild", {
          params: {
            child_id: localStorage.getItem("child_id"),
          },
        })
        .then((res) => {
          this.childName = res.data.child_name;
        });
    },
    // 달력에 이미지 넣기
    insertImg() {
      // for (let)
    },
    // 그 달의 식단 기록 가져오기
    getFoodRecord() {
      
      http
        .get("foodRecord/getMyMonthlyFoodRecord", {
          params: {
            foodRecord_childId: localStorage.getItem("child_id"),
            month: this.month,
            year: this.year,
          },
        })
        .then((res) => {
          this.backgroundImg = [];
          this.foodRecordList = res.data;
          for (let i = 1; i < this.currentMonthEnd + 1; i++) {
            const filterImg = this.foodRecordList.filter((data) => {
              if (this.month < 10 && i < 10) {
                return data.foodRecord_date.includes(
                  this.year + "-" + "0" + this.month + "-" + "0" + i
                );
              } else if (this.month < 10 && i > 10) {
                return data.foodRecord_date.includes(
                  this.year + "-" + "0" + this.month + "-" + i
                );
              } else if (this.month > 10 && i < 10) {
                return data.foodRecord_date.includes(
                  this.year + "-" + this.month + "-" + "0" + i
                );
              } else {
                return data.foodRecord_date.includes(
                  this.year + "-" + this.month + "-" + i
                );
              }
            });
            if (filterImg.length === 0) {
              this.backgroundImg.push("");
            } else {
              const randomNumber = Math.floor(Math.random() * filterImg.length);
              const previewImg =
                "data:image/jpeg;base64," +
                filterImg[randomNumber].foodRecord_media;
              this.backgroundImg.push(previewImg);
            }
          }
        });
    },
    daily(event) {
      let day;
      let month;
      if (String(event).length === 1) {
        day = "0" + String(event);
      } else {
        day = String(event);
      }
      if (String(this.month).length === 1) {
        month = "0" + String(this.month);
      } else {
        month = String(this.month);
      }
      const selectDate = String(this.year) + month + day;
      const dailyFood = this.foodRecordList.filter((data) => {
        return data.foodRecord_date.includes(
          this.year + "-" + month + "-" + day
        );
      });
      this.$router.push({
        name: "FoodRecordDetail",
        params: {
          today: selectDate,
          year: String(this.year),
          month: month,
          day: day,
          dailyFood: dailyFood,
        },
      });
    },
    calendarData(arg) {
      // 인자를 추가
      if (arg < 0) {
        // -1이 들어오면 지난 달 달력으로 이동
        this.month -= 1;
      } else if (arg === 1) {
        // 1이 들어오면 다음 달 달력으로 이동
        this.month += 1;
      }
      if (this.month === 0) {
        // 작년 12월
        this.year -= 1;
        this.month = 12;
      } else if (this.month > 12) {
        // 내년 1월
        this.year += 1;
        this.month = 1;
      }
      const [
        monthFirstDay,
        monthLastDate,
        lastMonthLastDate,
      ] = this.getFirstDayLastDate(this.year, this.month);
      this.dates = this.getMonthOfDays(
        monthFirstDay,
        monthLastDate,
        lastMonthLastDate
      );
    },
    getFirstDayLastDate(year, month) {
      const firstDay = new Date(year, month - 1, 1).getDay(); // 이번 달 시작 요일
      const lastDate = new Date(year, month, 0).getDate(); // 이번 달 마지막 날짜
      this.currentMonthEnd = lastDate;
      let lastYear = year;
      let lastMonth = month - 1;
      if (month === 1) {
        lastMonth = 12;
        lastYear -= 1;
      }
      const prevLastDate = new Date(lastYear, lastMonth, 0).getDate(); // 지난 달 마지막 날짜
      this.getFoodRecord();
      return [firstDay, lastDate, prevLastDate];
    },
    getMonthOfDays(monthFirstDay, monthLastDate, prevMonthLastDate) {
      let day = 1;
      let prevDay = prevMonthLastDate - monthFirstDay + 1;
      const dates = [];
      let weekOfDays = [];
      while (day <= monthLastDate) {
        if (day === 1) {
          // 1일이 어느 요일인지에 따라 테이블에 그리기 위한 지난 셀의 날짜들을 구할 필요가 있다.
          for (let j = 0; j < monthFirstDay; j += 1) {
            if (j === 0) this.lastMonthStart = prevDay; // 지난 달에서 제일 작은 날짜
            weekOfDays.push(prevDay);
            prevDay += 1;
          }
        }
        weekOfDays.push(day);
        if (weekOfDays.length === 7) {
          // 일주일 채우면 
          dates.push(weekOfDays);
          weekOfDays = []; // 초기화
        }
        day += 1;
      }
      const len = weekOfDays.length;
      if (len > 0 && len < 7) {
        for (let k = 1; k <= 7 - len; k += 1) {
          weekOfDays.push(k);
        }
      }
      if (weekOfDays.length > 0) dates.push(weekOfDays); // 남은 날짜 추가
      this.nextMonthStart = weekOfDays[0]; // 이번 달 마지막 주에서 제일 작은 날짜
      return dates;
    },
  },
};
</script>

<style>
.FoodRecord-container {
  height: 100vh;
  width: 100vw;
  display: flex;
  align-items: center;
  overflow: visible;
  justify-content: center;
  /* display: inline-block; */
}
.cursor:not(.has-text-danger, .has-text-info-dark):hover {
  cursor: pointer;
  color: #40aa57;
}
.has-text-info-dark {
  visibility: hidden;
}
.has-text-primary {
  font-size: 1.1em;
  font-weight: bold;
  color: #f3951b;
  -webkit-text-stroke: 0.1px black;
  z-index: 11;
}
.has-text-danger {
  visibility: hidden;
}
.calrender-box {
  font-family: "BMDOHYEON", sans-serif;
  width: 90%;
  overflow: visible;
  display: flex;
  flex-direction: column;
  border: 1px solid #f3951b;
  border-radius: 5%;
}
.calrender-box #calrender-top {
  display: flex;
  justify-content: space-evenly;
  height: 8vh;
  align-items: center;
  background-color: #f3951b;
  color: white;
  font-weight: 500;
  text-align: center;
  font-size: 1.2rem;
  letter-spacing: 2px;
  border-radius: 12px 12px 12px 12px;
}

.calrender-box #calrender-top button {
  background-color: #f3951b;
  border: #f3951b;
  outline: none;
}

.calrender-box #calrender-table {
  /* height: 100%; */
  background-color: white;
  border-radius: 16px 16px 16px 16px;
  /* z-index: -1; */
}
.calrender-box #calrender-table tbody {
  /* height: 100vh; */
  border-radius: 12px 12px 12px 12px;
  z-index: 10;
}

.calrender-box #calrender-table th {
  font-size: 1em;
  color: #40aa57;
}

.calrender-box #calrender-table td {
  text-align: center;
  font-size: 1.2rem;
  font-weight: 600;
}
.day {
  width: 46px;
  padding: 0;
  height: 46px;
  display: flex;
  justify-content: center;
  align-items: center;
  background-size: cover;
  border-radius: 50%;
}
@font-face {
  font-family: "BMDOHYEON";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_one@1.0/BMDOHYEON.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}
.record-title {
  position: fixed;
  text-align: center;
  top: 15vh;
  line-height: 1.5em;
  font-size: 2em;
}
</style>

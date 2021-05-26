<template>
  <section class="calrender-section">
    <!-- <div class="calrender-container"> -->
      <div id="calrender-top">
        <button 
        @click="calendarData(-1)"><i class="fas fa-chevron-left"></i></button>
        {{ year }}년 {{ month }}월
        <button 
        @click="calendarData(1)"><i class="fas fa-chevron-right"></i></button>
      </div>
      
      <table id="calrender-table">
        <thead>
          <th v-for="day in days" :key="day" >{{ day }}</th>
        </thead>
        <tbody>
          <tr v-for="(date, idx) in dates" :key="idx">
            <td
              class="cursor"
              v-for="(day, secondIdx) in date"
              :key="secondIdx"
              :class="{ 'has-text-info-dark': idx === 0 && day >= lastMonthStart,
              'has-text-danger': dates.length - 1 === idx && nextMonthStart > day,
              'has-text-primary': day === today && month === currentMonth && year === currentYear
              }"
              @click="daily(day)"
            >
              {{ day }}
            </td>
          </tr>
        </tbody>
      </table>
    <!-- </div> -->
  </section>
</template>

<script>
export default {
  data() {
    return {
      days: [
        'SUN',
        'MON',
        'TUE',
        'WED',
        'THU',
        'FRI',
        'SAT',
      ],
      dates: [],
      currentYear: 0,
      currentMonth: 0,
      year: 0,
      month: 0,
      lastMonthStart: 0,
      nextMonthStart: 0,
      today: 0,
    };
  },
  created() { // 데이터에 접근이 가능한 첫 번째 라이프 사이클
    const date = new Date();
    this.currentYear = date.getFullYear(); // 이하 현재 년, 월 가지고 있기
    this.currentMonth = date.getMonth() + 1;
    this.year = this.currentYear;
    this.month = this.currentMonth;
    this.today = date.getDate(); // 오늘 날짜
    this.calendarData();
    this.daily(this.today)
  },
  methods: {
    daily(event) {
      let day;
      if (String(event).length === 1){
        day = '0' + String(event)
      } else {
        day = String(event)
      }
      const params = {
        year : String(this.year),
        month : '0' + String(this.month),
        day : day
      }
      this.$emit('update', params)
    },
    calendarData(arg) { // 인자를 추가
      if (arg < 0) { // -1이 들어오면 지난 달 달력으로 이동
        this.month -= 1;
      } else if (arg === 1) { // 1이 들어오면 다음 달 달력으로 이동
        this.month += 1;
      }
      if (this.month === 0) { // 작년 12월
        this.year -= 1;
        this.month = 12;
      } else if (this.month > 12) { // 내년 1월
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
        lastMonthLastDate,
      );
    },
    getFirstDayLastDate(year, month) {
      const firstDay = new Date(year, month - 1, 1).getDay(); // 이번 달 시작 요일
      const lastDate = new Date(year, month, 0).getDate(); // 이번 달 마지막 날짜
      let lastYear = year;
      let lastMonth = month - 1;
      if (month === 1) {
        lastMonth = 12;
        lastYear -= 1;
      }
      const prevLastDate = new Date(lastYear, lastMonth, 0).getDate(); // 지난 달 마지막 날짜
      return [firstDay, lastDate, prevLastDate];
    },
    getMonthOfDays(
      monthFirstDay,
      monthLastDate,
      prevMonthLastDate,
    ) {
      let day = 1;
      let prevDay = (prevMonthLastDate - monthFirstDay) + 1;
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
}
</script>

<style>
.cursor:not(.has-text-danger, .has-text-info-dark):hover {
  cursor: pointer;
  color:rgb(174, 213, 121);
}
.has-text-info-dark {
  color:rgb(176, 173, 173);
}
.has-text-primary {
  font-size: 1.1em;
  font-weight: bold;
  color:rgb(108, 181, 161);
}
.has-text-danger{
  color:rgb(176, 173, 173);
}
.calrender-section {
  font-family: "Baloo";
  width: 90%;
  display: flex;
  flex-direction: column;
  border: 1px solid #FF6B6B;
  border-radius: 5%;
}
.calrender-section  #calrender-top {
  display: flex;
  justify-content: space-evenly;
  height: 8vh;
  align-items: center;
  background-color: #FF6B6B;
  color:white;
  font-weight: 800;
  text-align: center;
  border-radius: 12px 12px  0 0;
}

.calrender-section  #calrender-top button {
  background-color: #FF6B6B;
  border: #FF6B6B;
  outline: none;
}

/* .calrender-section  #calrender-table  {
  height: 100%;
} */
/* .calrender-section  #calrender-table tbody{
  height: 100vh;
} */

.calrender-section  #calrender-table th {
  font-size: 1.0em;
  color: #FF6B6B;
}

.calrender-section  #calrender-table td {
  text-align: center;
  height: 8vh;
  font-size:1.2rem;
  font-weight: 800;
  
}

</style>
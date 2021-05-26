<template>
  <div class="adminchild-container">
    <div id="child">
      <div class="table-container">
        <div class="inner">
          <div class="table__header-box">
            <span class="table__header">자녀 목록</span>
          </div>
          <table>
            <thead>
              <tr>
                <th>아이디</th>
                <th>이름</th>
              </tr>
            </thead>
            <tbody>
              <tr
                @click="getMyChildParent(item.child_id)"
                v-for="(item, idx) in allChild"
                :key="idx"
              >
                <td>{{ item.child_id }}</td>
                <td>{{ item.child_name }}</td>
              </tr>
            </tbody>
          </table>
        </div>
        <!-- <div class="table__caption">
          <h2 class="table__header">자녀목록</h2> -->
        <!-- <p class="table__header--sub">Sub header</p> -->
        <!-- </div> -->
      </div>
    </div>
    <div class="childinfo" v-if="infocheck">
      <div><h3>상세정보</h3></div>
      <div></div>
      <div>child_id</div>
      <div>{{ childInfo.child_id }}</div>
      <div>parent_id</div>
      <div>{{ childInfo.child_parentId }}</div>
      <div>이름</div>
      <div>{{ childInfo.child_name }}</div>
      <div>성별</div>
      <div>{{ childInfo.child_sex }}</div>
      <div>지역코드</div>
      <div>{{ childInfo.child_area }}</div>
      <div>학교이름</div>
      <div>{{ childInfo.child_schoolName }}</div>
      <div>생년월일</div>
      <div>{{ childInfo.child_birth }}</div>
      <div>키</div>
      <div>{{ childInfo.child_height }}</div>
      <div>몸무게</div>
      <div>{{ childInfo.child_weight }}</div>
      <div>child_BMI</div>
      <div>{{ childInfo.child_BMI }}</div>
    </div>
    <AdminTab @update="movepath" />
  </div>
</template>

<script>
import AdminTab from "./AdminTab";
import http from "@/api/http-common";
export default {
  components: {
    AdminTab: AdminTab,
  },
  data() {
    return {
      allChild: null,
      childInfo: [],
      infocheck: false,
    };
  },
  methods: {
    getAllChild() {
      http.get("/admin/getAllChild").then((res) => {
        this.allChild = res.data;
      });
    },
    getMyChildParent(event) {
      http
        .get("/child/getMyChild/", {
          params: {
            child_id: event,
          },
        })
        .then((res) => {
          this.childInfo = res.data;
          this.infocheck = true;
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
    this.getAllChild();
  },
};
</script>

<style>
.adminchild-container {
  display: flex;
  width: 100vw;
  height: 100vh;
  font-family: "BMDOHYEON";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_one@1.0/BMDOHYEON.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
  flex-direction: column;
  align-items: flex-start;
  justify-content: flex-start;

  /* border:1px solid black; */
}
.adminchild-container #child {
  margin-top: 15%;
  height: 40%;
  display: flex;
}

.adminchild-container .childinfo {
  display: grid;
  height: 20%;
  grid-template-columns: 2fr 2fr;
  width: 90vw;
  row-gap: 0.2rem;
  column-gap: 0.2rem;
  justify-content: center;
  padding-left: 2rem;
}
/* 
.table-container {
  margin-top: 2%;
  position: relative;
  top: 1rem;
  width: 100%;
  max-width: 100%;
  min-width: 100%;
  margin: 0 auto 5em;
  overflow: scroll;
}
.table-container .inner {
  background-color: #3b414e;
  border-radius: 0.6em;
  overflow: hidden;
  box-shadow: 1px 2px 2px rgba(0, 0, 0, 0.2);
}
.table-container table {
  table-layout: fixed;
  margin-top: 10%;
  border-collapse: collapse;
  width: 100%;
  text-align: left;
  background-color: white;
  overflow: hidden;
}
.table-container table tbody,
.table-container table thead {
  padding: 0 1em;
}
.table-container table tr:not(:last-child) {
  border-bottom: 1px solid #464c59;
}
.table-container tbody tr:hover {
  background-color: rgba(255, 255, 255, 0.1);
}
.table-container thead tr {
  border-bottom: 1px solid #464c59;
}
.table-container thead th {
  padding: 4em 1em 1em;
}
.table-container th,
.table-container td {
  padding: 0.6em 1em;
  word-break: break-all;
}
.table-container.one tr:not(:last-child) {
  border-bottom: 1px solid rgba(255, 255, 255, 0.15);
}
.table-container.one thead tr {
  border-bottom: 1px solid rgba(255, 255, 255, 0.15);
}
.table__caption {
  position: absolute;
  top: 0em;
  left: 50%;
  transform: translateX(-50%);
  width: 100%;
  margin: 0 auto;
  max-width: 95%;
  margin: 0 auto;
  padding: 0.6em 1em;
  border-radius: 0.8em;
  background-color: #4fc08d;
  opacity: 90%;
  box-shadow: 0 5px 13px rgba(0, 0, 0, 0.4);
}
.table__header {
  font-family: inherit;
  font-size: 1.5em;
  font-weight: 300;
}
.table__header--sub {
  font-size: 0.9em;
}

::selection {
  color: #fff;
  background-color: rgba(255, 255, 255, 0.1);
} */
</style>
<template>
  <div class="admin-container">
    <div id="parent">
      <div class="table-container">
        <div class="inner">
          <div class="table__header-box">
            <span class="table__header">보호자 목록</span>
          </div>
          <table>
            <thead>
              <tr>
                <th>아이디(pk)</th>
                <th>이메일</th>
              </tr>
            </thead>
            <tbody>
              <tr
                @click="getParentInfo(item.parent_id)"
                v-for="(item, idx) in allParent"
                :key="idx"
              >
                <td>{{ item.parent_id }}</td>
                <td>{{ item.parent_email }}</td>
              </tr>
            </tbody>
          </table>
        </div>
        <!-- <div class="table__caption">
          <h2 class="table__header">보호자목록</h2> -->
        <!-- <p class="table__header--sub">Sub header</p> -->
        <!-- </div> -->
      </div>
    </div>
    <div class="parentinfo" v-if="infocheck">
      <div><h3>상세정보</h3></div>
      <div></div>
      <div>parent_id</div>
      <div>{{ parentInfo.parent_id }}</div>
      <div>이메일</div>
      <div>{{ parentInfo.parent_email }}</div>
      <div>이름</div>
      <div>{{ parentInfo.parent_name }}</div>
      <div>생년월일</div>
      <div>{{ parentInfo.parent_birth }}</div>
      <div>전화번호</div>
      <div>{{ parentInfo.parent_tel }}</div>
    </div>
    <AdminTab @update="movepath" />
  </div>
</template>

<script>
import AdminTab from "./AdminTab";
import http from "@/api/http-common";
// import { component } from 'vue/types/umd';

export default {
  components: {
    AdminTab: AdminTab,
  },
  data() {
    return {
      allParent: null,
      parentInfo: [],
      infocheck: false,
    };
  },
  methods: {
    getAllParent() {
      http.get("/admin/getAllParent").then((res) => {
        // console.log(res)
        this.allParent = res.data;
      });
    },

    getParentInfo(event) {
      http
        .get("/admin/getParentInfo/", {
          params: {
            parent_id: event,
          },
        })
        .then((res) => {
          // console.log(res.data)
          this.parentInfo = res.data;
          this.infocheck = true;
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
          console.log(res.data);
          this.childtInfo = res.data;
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
    this.getAllParent();
  },
};
</script>

<style>
.admin-container {
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
.admin-container #parent {
  margin-top: 15%;
  height: 40%;
  display: flex;
}

.admin-container .parentinfo {
  display: grid;
  height: 20%;
  grid-template-columns: 2fr 2fr;
  width: 90vw;
  row-gap: 0.2rem;
  column-gap: 0.2rem;
  justify-content: center;
  padding-left: 2rem;
}

.table-container {
  margin-top: 2%;
  position: relative;
  top: 1rem;
  width: 100%;
  max-width: 100%;
  min-width: 100%;
  margin: 0 auto 5em;
  overflow: scroll;
  border-radius: 10px;
}
.table-container .inner {
  /* padding: 0 .8em; */
  /* background-color: #40aa57; */
  border-radius: 10px;
  overflow: hidden;
  box-shadow: 1px 2px 2px rgba(0, 0, 0, 0.2);
  text-align: center;
  width: 95%;
  left: 2.5%;
  margin-top: 3%;
}
.table-container table {
  table-layout: fixed;
  /* margin-top: 3%; */
  border-collapse: collapse;
  width: 100%;
  text-align: center;
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
  /* padding: 4em 1em 1em; */
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
.table__header-box {
  background-color: #40aa57;
  width: 100%;
}
.table__header {
  /* position: relative; */
  font-size: 2em;
  margin-top: 3%;
}
.table__header--sub {
  font-size: 0.9em;
}
.empty-box {
  display: none;
}
::selection {
  color: #fff;
  background-color: rgba(255, 255, 255, 0.1);
}
</style>
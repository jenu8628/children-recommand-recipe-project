import Vue from "vue";
import VueRouter from "vue-router";

//  Main
import MainPage from "../components/Main/MainPage";

// Recipe
import RecipeDetail from "@/components/Recipe/RecipeDetail.vue";
import RegisterRecipePage from "../components/Recipe/RegisterRecipePage";

// MyPage
import MyPage from "../components/MyPage/MyPage";
import MyPageAcademicSchedule from "../components/MyPage/MyPageAcademicSchedule";
import MyPageChildInfo from "../components/MyPage/MyPageChildInfo";
import MyPageFavorites from "@/components/MyPage/MyPageFavorites.vue";
import MyPageModify from "../components/MyPage/MyPageModify";
import MyPageRefrigerator from "../components/MyPage/MyPageRefrigerator";
import About from "../components/MyPage/About";
import MyPageAllergy from "../components/MyPage/ChildAllergy";

// User
import Join from "../components/User/Join";
import Login from "../components/User/Login";
import ChildChoice from "../components/User/ChildChoice";
import ChildLogin from "../components/User/ChildLogin";
import SelectPreference from "../components/User/SelectPreference";
import SelectPreferenceInfo from "../components/User/SelectPreferenceInfo";
import AddChild from "../components/User/AddChild";
import Loading from "../components/User/Loading";

// TodoList
import TodoList from "../components/TodoList/TodoList";

//Search
import Search from "../components/Search/Search";

//Chat
import Chat from "@/components/Chat/Chat";
import ChatList from "@/components/Chat/ChatList";

//FoodRecord
import FoodRecord from "@/components/FoodRecord/FoodRecord";
import FoodRecordDetail from "@/components/FoodRecord/FoodRecordDetail";

// admin
import Admin from "../components/Admin/Admin";
import AdminChild from "../components/Admin/AdminChild";
import TodayTip from "../components/Admin/TodayTip";
Vue.use(VueRouter);

const requireAuth = () => (to, from, next) => {
  if (localStorage.getItem("parent_id") == undefined) {
    alert("로그인이 필요한 서비스입니다!");

    return next("/login");
  }
  next();
};

const routes = [
  // Main
  {
    path: "/",
    name: "MainPage",
    component: MainPage,
  },
  // Recipe
  {
    path: "/recipeDetail",
    name: "RecipeDetail",
    component: RecipeDetail,
    props: true,
  },
  {
    path: "/registerrecipe",
    name: "RegisterRecipePage",
    component: RegisterRecipePage,
    beforeEnter: requireAuth(),
  },
  // MyPage
  {
    path: "/mypage",
    name: "MyPage",
    component: MyPage,
    beforeEnter: requireAuth(),
  },
  {
    path: "/mypageacademicschedule",
    name: "MyPageAcademicSchedule",
    component: MyPageAcademicSchedule,
    beforeEnter: requireAuth(),
  },
  {
    path: "/mypagechildinfo",
    name: "MyPageChildInfo",
    component: MyPageChildInfo,
    beforeEnter: requireAuth(),
  },
  {
    path: "/mypagefavorites",
    name: "MyPageFavorites",
    component: MyPageFavorites,
    beforeEnter: requireAuth(),
  },
  {
    path: "/mypagemodify",
    name: "MyPageModify",
    component: MyPageModify,
    beforeEnter: requireAuth(),
  },
  {
    path: "/mypagerefrigerator",
    name: "MyPageRefrigerator",
    component: MyPageRefrigerator,
    beforeEnter: requireAuth(),
  },
  {
    path: "/about",
    name: "About",
    component: About,
  },
  {
    path: "/allergy",
    name: "MyPageAllergy",
    component: MyPageAllergy,
    beforeEnter: requireAuth(),
  },
  // User
  {
    path: "/join",
    name: "Join",
    component: Join,
  },
  {
    path: "/login",
    name: "Login",
    component: Login,
  },
  {
    path: "/childchoice",
    name: "ChildChoice",
    component: ChildChoice,
  },
  {
    path: "/selectpreference",
    name: "SelectPreference",
    component: SelectPreference,
    beforeEnter: requireAuth(),
  },
  {
    path: "/selectpreference/info",
    name: "SelectPreferenceInfo",
    component: SelectPreferenceInfo,
    beforeEnter: requireAuth(),
  },
  {
    path: "/childlogin",
    name: "ChildLogin",
    component: ChildLogin,
  },
  {
    path: "/addchild",
    name: "AddChild",
    component: AddChild,
    beforeEnter: requireAuth(),
  },
  {
    path: "/loading",
    name: "Loading",
    component: Loading,
  },
  //TodoList
  {
    path: "/todolist",
    name: "TodoList",
    component: TodoList,
    beforeEnter: requireAuth(),
  },
  // Search
  {
    path: "/search",
    name: "Search",
    component: Search,
  },
  // Chat
  {
    path: "/chat",
    name: "Chat",
    component: Chat,
    beforeEnter: requireAuth(),
  },
  {
    path: "/chatlist",
    name: "ChatList",
    component: ChatList,
    beforeEnter: requireAuth(),
  },
  // FoodRecord
  {
    path: "/foodrecord",
    name: "FoodRecord",
    component: FoodRecord,
    beforeEnter: requireAuth(),
  },
  {
    path: "/foodrecorddetail",
    name: "FoodRecordDetail",
    component: FoodRecordDetail,
    beforeEnter: requireAuth(),
  },
  // Admin
  {
    path: "/admin",
    name: "Admin",
    component: Admin,
  },
  {
    path: "/adminchild",
    name: "AdminChild",
    component: AdminChild,
  },
  {
    path: "/admin/tip",
    name: "TodayTip",
    component: TodayTip,
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;

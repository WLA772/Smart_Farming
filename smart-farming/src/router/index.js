import Vue from "vue";
import VueRouter from "vue-router";

import intro_home from "../views/intro_home.vue";
import knowledge_site from "../views/knowledge_site.vue";
import login_in from "../views/login_in.vue";
import register_in from "../views/register_in.vue";
import profile_in from "../views/profile_in.vue";
import change_pwd from "../views/change_pwd.vue";
import knowledge_details from "../views/knowledge_details.vue";
import favourite_post_in from "../views/favourite_post.vue";
import store from "@/store";

 import shoppingCart from "../views/shoppingCart.vue";
//  import cartView from "../views/cartView.vue";
  import orderView from "../views/orderView.vue"

import productView from "../views/productView.vue";
import product_details from "../views/product_detail.vue";

import admin_by from "../views/admin_by.vue";
import admin_by_prod from "../views/admin_by_product.vue";
import admin_add_product from "../views/admin_add_product.vue";

import admin_create_knowledge from "../views/admin_create_knowledge.vue";
import admin_user_list from "../views/admin_user_list.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "intro_home",
    component: intro_home,
  },

  {
    path: "/knowledge",
    name: "knowledge_site",
    component: knowledge_site,
  },
  {
    path: "/login",
    name: "login_in",
    component: login_in,
  },
  {
    path: "/register",
    name: "register_in",
    component: register_in,
  },
  {
    path: "/profile",
    name: "profile_in",
    component: profile_in,
  },
  {
    path: "/knowledge_details/:id",
    name: "knowledge_details",
    component: knowledge_details,
    meta: {
      requiresAuth: true,
    },
  },

  {
    path: "/favourite_post",
    name: "favourtie_post_in",
    component: favourite_post_in,
  },

  {
    path: "/changePwd",
    name: "change_pwd",
    component: change_pwd,
    meta: {
      requiresAuth: true,
    },
  },

  {
    path: "/product",
    name: "productView",
    component: productView,
  },
  {
    path: "/product_detail/:id",
    name: "product_detail",
    component: product_details,
    meta: {
      requiresAuth: true,
    },
  },

  // ----------- Admin
  {
    path: "/admin",
    name: "admin_by",
    component: admin_by,
    meta: {
      requiresAuth: true,
      requiresAdmin: true,
    },
  },

  {
    path: "/admin_byproduct",
    name: "admin_by_prod",
    component: admin_by_prod,
    meta: {
      requiresAuth: true,
      requiresAdmin: true,
    },
  },

  {
    path: "/admin/create_knowledge",
    name: "admin_create_knowledge",
    component: admin_create_knowledge,
    meta: {
      requiresAuth: true,
      requiresAdmin: true,
    },
  },

  {
    path: "/admin/user_list",
    name: "admin_user_list",
    component: admin_user_list,
    meta: {
      requiresAuth: true,
      requiresAdmin: true,
    },
  },

  {
    path: "/admin/add_product",
    name: "admin_add_product",
    component: admin_add_product,
    meta: {
      requiresAuth: true,
      requiresAdmin: true,
    },
  },

  //shopping cart
   {
     path: "/cart",
     name: "ShoppingCart",
     component: shoppingCart,
    meta: {
      requiresAuth: true,
     },
   },


   {
     path: "/orderView",
    name: "OrderView",
     component: orderView,
     meta: {
       requiresAuth: true,
     },
   },


  // {
  //   path: "/myCart",
  //   name: "CartView",
  //   component: cartView,
  //   meta: {
  //     requiresAuth: true,
  //   },
  // }
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

router.beforeEach((to, from, next) => {
  // let loginUser = router.app.$store.getters.loginUser;
  // let isLogin = router.app.$store.getters.isLogin;

  let loginUser = store.getters.loginUser;
  let isLogin = store.getters.isLogin;

  console.log(loginUser, "wailin");
  console.log(isLogin, "thu");
  // from - Home
  // to - Profile
  // Need to be login, But is not login
  if (to.meta.requiresAuth == true && !isLogin) {
    next({ path: "/" });
  }

  // Need to be login, Need to be admin
  else if (
    to.meta.requiresAuth == true &&
    to.meta.requiresAdmin == true &&
    loginUser.role != "admin"
  ) {
    next({ path: "/" });
  }

  // If All Okay
  else {
    next();
  }
});
export default router;

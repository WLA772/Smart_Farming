<template>
    <v-card class="mx-auto">
      <v-navigation-drawer permanent>
        <v-list dense nav>
          <v-list-item
            v-for="item in menuItemList"
            :key="item.title"
            link
            @click="goToRoute(item.path)"
          >
            <!-- Icon -->
            <v-list-item-icon>
              <v-icon color="primary">{{ item.icon }}</v-icon>
            </v-list-item-icon>
  
            <!-- Title -->
            <v-list-item-content>
              <v-list-item-title>{{ item.title }}</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list>
      </v-navigation-drawer>
    </v-card>
  </template>
  
  <script>
  export default {
    name: "sidebar_admin",
  
    data: () => ({
      menuItemList: [
        {
          title: "Admin-By-Knowledge",
          icon: "admin_panel_settings",
          path: "/admin",
          
        },


        {
          title: "Admin-By-Product",
          icon: "admin_panel_settings",
          path: "/admin_byproduct",
          
        },



        {
          title: "Create Knowledge",
          icon: "lightbulb",
          path: "/admin/create_knowledge",
        },

        {
          title: "Add Product",
          icon: "add_box",
          path: "/admin/add_product",
        },

        {
          title: "User List",
          icon: "people",
          path: "/admin/user_list",
        },
        // {
        //   title: "Record List",
        //   icon: "visibility",
        //   path: "/admin/record_list",
        // },
      ],
    }),
  
    created() {},
  
    methods: {
      goToRoute(path) {
        // If Current Path is same with Clicked Path, No Go to Route
        if (this.$route.path != path) {
          this.$router.push({ path: path });
        }
      },
    },
  };
  </script>
  
  <style scoped></style>
  
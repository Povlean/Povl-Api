/**
 * @name umi 的路由配置
 * @description 只支持 path,component,routes,redirect,wrappers,name,icon 的配置
 * @param path  path 只支持两种占位符配置，第一种是动态参数 :id 的形式，第二种是 * 通配符，通配符只能出现路由字符串的最后。
 * @param component 配置 location 和 path 匹配后用于渲染的 React 组件路径。可以是绝对路径，也可以是相对路径，如果是相对路径，会从 src/pages 开始找起。
 * @param routes 配置子路由，通常在需要为多个路径增加 layout 组件时使用。
 * @param redirect 配置路由跳转
 * @param wrappers 配置路由组件的包装组件，通过包装组件可以为当前的路由组件组合进更多的功能。 比如，可以用于路由级别的权限校验
 * @param name 配置路由的标题，默认读取国际化文件 menu.ts 中 menu.xxxx 的值，如配置 name 为 login，则读取 menu.ts 中 menu.login 的取值作为标题
 * @param icon 配置路由的图标，取值参考 https://ant.design/components/icon-cn， 注意去除风格后缀和大小写，如想要配置图标为 <StepBackwardOutlined /> 则取值应为 stepBackward 或 StepBackward，如想要配置图标为 <UserOutlined /> 则取值应为 user 或者 User
 * @doc https://umijs.org/docs/guides/routes
 */
export default [
  {
    path: '/user',
    layout: false,
    routes: [
      {
        name: 'login',
        path: '/user/login',
        component: './User/Login',
      },
      {
        name: 'register',
        path: '/user/register',
        component: './User/Register',
      },
    ],
  },
  {
    icon: 'smile',
    path: '/basic',
    name: 'basic',
    component: './BasicFunc',
  },
  {
    path: '/interface_info/:id',
    name: 'through',
    icon: 'smile',
    component: './InterfaceInfo',
    hideInMenu: true
  },
  {
    path: '/',
    name: 'usingInterface',
    icon: 'smile',
    component: './Index',
  },
  {
    path: '/profile',
    name: 'user-center',
    icon: 'crown',
    component: './Profile',
  },
  {
    path: '/admin',
    name: 'management',
    icon: 'crown',
    access: 'canAdmin',
    component: './TableList',
    // routes: [
    //   {
    //     path: '/admin',
    //     redirect: '/admin/sub-page',
    //   },
    //   {
    //     path: '/admin/sub-page2',
    //     name: 'test',
    //     component: './404',
    //   },
    //   {
    //     path: '/admin/list',
    //     name: 'management',
    //     component: './TableList',
    //   },
    // ],
  },
  {
    icon: 'crown',
    access: 'canAdmin',
    path: '/analysis',
    name: 'analysis',
    component: './Analysis',
  },
  {
    icon: 'smile',
    path: '/community',
    name: 'community',
    component: './Community',
  },
  {
    access: 'canAdmin',
    icon: 'crown',
    path: '/log',
    name: 'log',
    component: './LogManage',
  },
  {
    // layout: false,
    hideInMenu: true,
    path: '/weather',
    name: 'weather',
    component: './IDE/Weather',
  },
  {
    // layout: false,
    hideInMenu: true,
    path: '/music',
    name: 'music',
    component: './IDE/Music',
  },
  {
    // layout: false,
    hideInMenu: true,
    path: '/book',
    name: 'book',
    component: './IDE/Book',
  },
  {
    // layout: false,
    hideInMenu: true,
    path: '/avatar',
    name: 'avatar',
    component: './IDE/Avatar',
  },
  {
    // layout: false,
    hideInMenu: true,
    path: '/musicSearch',
    name: 'musicSearch',
    component: './IDE/MusicSearch',
  },
  {
    // layout: false,
    hideInMenu: true,
    path: '/news',
    name: 'news',
    component: './IDE/News',
  },
  {
    // layout: false,
    hideInMenu: true,
    path: '/words',
    name: 'words',
    component: './IDE/Words',
  },
  {
    // layout: false,
    hideInMenu: true,
    path: '/stock',
    name: 'stock',
    component: './IDE/Stock',
  },
  {
    hideInMenu: true,
    path: '/article',
    name: 'article',
    component: './OnArticle',
  },
  {
    hideInMenu: true,
    path: '/basicFunc2',
    name: 'basicFunc2',
    component: './BasicFunc2',
  },
  {
    hideInMenu: true,
    path: '/basicFunc3',
    name: 'basicFunc3',
    component: './BasicFunc3',
  },
  {
    path: '*',
    layout: false,
    component: './404',
  },
];

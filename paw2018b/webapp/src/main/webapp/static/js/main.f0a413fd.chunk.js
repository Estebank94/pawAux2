(this.webpackJsonpfrontend=this.webpackJsonpfrontend||[]).push([[5],{10:function(e,t,n){"use strict";n.d(t,"a",(function(){return a}));var a={DO_SIGN_IN:"auth/DO_SIGN_IN",DO_SIGN_OUT:"auth/DO_SIGN_OUT",REGISTER_COMPLETED:"auth/REGISTER_COMPLETED",REGISTER_ERROR:"auth/REGISTER_ERROR",SIGN_IN_SUCCESS:"auth/SIGNIN_SUCCESS",SIGN_IN_ERROR:"auth/SIGNIN_ERROR"}},41:function(e,t,n){e.exports=n(58)},50:function(e,t,n){},53:function(e,t,n){},58:function(e,t,n){"use strict";n.r(t);var a=n(0),l=n.n(a),r=n(19),c=n(14),o=n(27),u=n(34),i=(n(50),n(7)),E=n(26),b=n.n(E),s=(n(53),n(28)),m=n.n(s),O=Object(a.lazy)((function(){return Promise.all([n.e(0),n.e(1),n.e(3),n.e(10),n.e(16)]).then(n.bind(null,288))})),h=Object(a.lazy)((function(){return n.e(15).then(n.bind(null,290))})),d=Object(a.lazy)((function(){return Promise.all([n.e(0),n.e(1),n.e(3),n.e(17)]).then(n.bind(null,298))})),p=Object(a.lazy)((function(){return Promise.all([n.e(0),n.e(1),n.e(2),n.e(14),n.e(20)]).then(n.bind(null,297))})),R=Object(a.lazy)((function(){return Promise.all([n.e(0),n.e(1),n.e(2),n.e(4),n.e(7)]).then(n.bind(null,294))})),S=Object(a.lazy)((function(){return Promise.all([n.e(0),n.e(1),n.e(13)]).then(n.bind(null,291))})),f=Object(a.lazy)((function(){return Promise.all([n.e(0),n.e(1),n.e(2),n.e(12),n.e(19)]).then(n.bind(null,299))})),_=Object(a.lazy)((function(){return Promise.all([n.e(0),n.e(1),n.e(2),n.e(4),n.e(11)]).then(n.bind(null,296))})),I=Object(a.lazy)((function(){return Promise.all([n.e(0),n.e(1),n.e(2),n.e(18)]).then(n.bind(null,292))})),j=Object(a.lazy)((function(){return Promise.all([n.e(0),n.e(2),n.e(21)]).then(n.bind(null,293))})),N=Object(a.lazy)((function(){return Promise.all([n.e(0),n.e(1),n.e(2),n.e(3),n.e(9)]).then(n.bind(null,295))})),G=function(){return l.a.createElement("div",{className:"body-background centered"},l.a.createElement(b.a,{sizeUnit:"px",size:75,color:"rgb(37, 124, 191)",loading:!0}))},g=function(){var e=Object(i.f)(O),t=Object(i.f)(h);return l.a.createElement("div",null,l.a.createElement(a.Suspense,{fallback:l.a.createElement(G,null)},l.a.createElement(e,null)),l.a.createElement(a.Suspense,{fallback:l.a.createElement(G,null)},l.a.createElement(m.a,null,l.a.createElement(i.b,{exact:!0,path:"/",component:d}),l.a.createElement(i.b,{exact:!0,path:"/specialists",component:p}),l.a.createElement(i.b,{exact:!0,path:"/specialist/:id",component:R}),l.a.createElement(i.b,{exact:!0,path:"/login",component:S}),l.a.createElement(i.b,{exact:!0,path:"/register/:role",component:f}),l.a.createElement(i.b,{exact:!0,path:"/account",component:_}),l.a.createElement(i.b,{exact:!0,path:"/confirm/:token",component:I}),l.a.createElement(i.b,{exact:!0,path:"/error/:error",component:j}),l.a.createElement(i.b,{exact:!0,path:"/complete",component:N}))),l.a.createElement(a.Suspense,{fallback:l.a.createElement(G,null)},l.a.createElement(t,null)))},y=n(6),z=n(35),P=n(36),T=n(16),x=n(10),C={auth:null,user:null,loading:!1,error:!1},D=Object(y.c)({auth:function(){var e=arguments.length>0&&void 0!==arguments[0]?arguments[0]:C,t=arguments.length>1?arguments[1]:void 0;switch(t.type){case x.a.DO_SIGN_IN:return Object(T.a)({},C,{loading:!0});case x.a.DO_SIGN_OUT:return C;case x.a.SIGN_IN_SUCCESS:case x.a.REGISTER_COMPLETED:return Object(T.a)({},e,{auth:t.data.auth,user:t.data.user,loading:!1,error:!1});case x.a.SIGN_IN_ERROR:case x.a.REGISTER_ERROR:return console.log("ERROR: "+t.error),Object(T.a)({},e,{loading:!1,error:!0});default:return e}}}),k=n(24),v=n(37),U=n.n(v),L=Object(P.createLogger)(),w={key:"root",storage:U.a},M=Object(k.a)(w,D),J=Object(y.d)(M,{},Object(y.a)(z.a,L)),q=Object(k.b)(J),A=document.querySelector("#root");Object(c.render)(l.a.createElement(o.a,{store:J},l.a.createElement(u.a,{loading:null,persistor:q},l.a.createElement(r.a,null,l.a.createElement(g,null)))),A)}},[[41,6,8]]]);
//# sourceMappingURL=main.f0a413fd.chunk.js.map
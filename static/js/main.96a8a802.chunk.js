(this.webpackJsonpclient=this.webpackJsonpclient||[]).push([[0],{51:function(e,n,t){},52:function(e,n,t){},83:function(e,n){},86:function(e,n,t){},87:function(e,n,t){},88:function(e,n,t){},95:function(e,n,t){"use strict";t.r(n);var c=t(0),r=t(43),a=t.n(r),o=(t(51),t(45)),s=t(2),i=t(97),u=(t(52),t(1)),l=function(e){return Object(u.jsx)("div",{className:"btn-page",children:Object(u.jsx)("div",{className:"btn-container",children:Object(u.jsx)("button",{className:"create-btn",onClick:function(){var n=Object(i.a)();e.history.push("/room/".concat(n))},children:"Create Room"})})})},d=t(25),m=t(26),j=t(44),h=t.n(j),f=(t(86),function(e){var n=e.messages;return Object(u.jsx)("div",{className:"messages_container",children:n.map((function(e,n){return e.yours?Object(u.jsx)("div",{className:"my_message_container",children:Object(u.jsx)("div",{className:"my_message",children:e.value})},n):Object(u.jsxs)("div",{className:"received_message_container",children:[Object(u.jsx)("div",{className:"received_message",children:e.value}),Object(u.jsx)("div",{id:"usr-r",children:"Username"})]},n)}))})}),b=(t(87),function(e){var n=Object(c.useRef)(),t=Object(c.useRef)(),r=Object(c.useRef)(),a=Object(c.useRef)(),o=Object(c.useState)(""),s=Object(m.a)(o,2),i=s[0],l=s[1],j=Object(c.useState)([]),b=Object(m.a)(j,2),v=b[0],O=b[1];function p(e){O((function(n){return[].concat(Object(d.a)(n),[{yours:!1,value:e.data}])}))}function g(e){var c=new RTCPeerConnection({iceServers:[{urls:"stun:nettverksprogg.eastus2.cloudapp.azure.com:3478"}]});return c.onicecandidate=C,c.onnegotiationneeded=function(){return function(e){n.current.createOffer().then((function(e){return n.current.setLocalDescription(e)})).then((function(){var c={target:e,caller:t.current.id,sdp:n.current.localDescription};t.current.emit("offer",c)})).catch((function(e){return console.log(e)}))}(e)},c}function x(e){n.current=g(),n.current.ondatachannel=function(e){a.current=e.channel,a.current.onmessage=p};var c=new RTCSessionDescription(e.sdp);n.current.setRemoteDescription(c).then((function(){})).then((function(){return n.current.createAnswer()})).then((function(e){return n.current.setLocalDescription(e)})).then((function(){var c={target:e.caller,caller:t.current.id,sdp:n.current.localDescription};t.current.emit("answer",c)}))}function w(e){var t=new RTCSessionDescription(e.sdp);n.current.setRemoteDescription(t).catch((function(e){return console.log(e)}))}function C(e){if(e.candidate){var n={target:r.current,candidate:e.candidate};t.current.emit("ice-candidate",n)}}function N(e){var t=new RTCIceCandidate(e);n.current.addIceCandidate(t).catch((function(e){return console.log(e)}))}function D(){a.current.send(i),O((function(e){return[].concat(Object(d.a)(e),[{yours:!0,value:i}])})),l("")}Object(c.useEffect)((function(){t.current=h.a.connect("http://localhost:8000"),t.current.emit("join room",e.match.params.roomID),t.current.on("other user",(function(e){!function(e){n.current=g(e),a.current=n.current.createDataChannel("sendChannel"),a.current.onmessage=p}(e),r.current=e})),t.current.on("user joined",(function(e){r.current=e})),t.current.on("offer",x),t.current.on("answer",w),t.current.on("ice-candidate",N)}),[]);return Object(u.jsxs)("div",{className:"container",children:[Object(u.jsxs)("h1",{children:["Chat room: ",e.match.params.roomID]}),Object(u.jsxs)("div",{className:"username",children:[Object(u.jsx)("input",{id:"name",type:"text",placeholder:"Username"}),Object(u.jsx)("button",{id:"name-btn",children:"Submit"})]}),Object(u.jsx)(f,{messages:v}),Object(u.jsxs)("div",{className:"send-msg",children:[Object(u.jsx)("textarea",{className:"msg-box",value:i,onChange:function(e){l(e.target.value)},onKeyDown:function(e){"Enter"===e.key&&D()},placeholder:"Write your message here..."}),Object(u.jsx)("button",{className:"btn",onClick:D,children:"Send"})]})]})});t(88);var v=function(){return Object(u.jsx)("div",{className:"App",children:Object(u.jsx)(o.a,{children:Object(u.jsxs)(s.c,{children:[Object(u.jsx)(s.a,{path:"/",exact:!0,component:l}),Object(u.jsx)(s.a,{path:"/room/:roomID",component:b})]})})})};Boolean("localhost"===window.location.hostname||"[::1]"===window.location.hostname||window.location.hostname.match(/^127(?:\.(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)){3}$/));a.a.render(Object(u.jsx)(v,{}),document.getElementById("root")),"serviceWorker"in navigator&&navigator.serviceWorker.ready.then((function(e){e.unregister()})).catch((function(e){console.error(e.message)}))}},[[95,1,2]]]);
//# sourceMappingURL=main.96a8a802.chunk.js.map
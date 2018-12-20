//index.js
//获取应用实例
Page({
  data: {
  },
onLoad:function(){
  var that=this
  //获取code
   wx.login({
     success:function(res){
       var code=res.code;
       that.setData({
         code:res.code
       })
       //console.log(res.code)

       //code换取openId
      //  var secret ='9561fa7ea9ddce35686b680be31d6b2a'
      //  var appId ='wx23e0f4db7a9320d9'
      //  wx.request({
      //    url: 'https://api.weixin.qq.com/sns/jscode2session?appid=' + appId + '&secret=' + secret + '&js_code=' + code + '&grant_type=authorization_code',
      //    success:function(res){
      //      console.log(res.data)
      //    }
      //  })
     }
   })
   //获取用户基本信息
   wx.getUserInfo({
     success:function(res){
       console.log(res)
      //  console.log(res.userInfo)
      //  console.log(res.encryptedData)
      //  console.log(res.iv)
     }
   })
},
  includePoints: function () {
    this.mapCtx.includePoints({
      padding: [10],
      points: [{
        latitude: 39.983900,
        longitude: 116.306910,
      }, {
        latitude: 39.920629,
        longitude: 116.433149,
      }]
    })
  },

  onReady: function(e) {
    this.mapCtx = wx.createMapContext('myMap',this)
    //this.getCenterLocation()
    this.mapCtx.moveToLocation()
  
    // 开机定位
  },
  // getCenterLocation: function() {
  //   this.mapCtx.getCenterLocation({
  //     success: function(res) {
  //       console.log(res.longitude)
  //       console.log(res.latitude) 
  //     }
  //   })
  // },
  moveToLocation: function() {
    this.mapCtx.moveToLocation()
    // 定位
  },
  translateMarker: function() {
    this.mapCtx.translateMarker({
      markerId: 1,
      autoRotate: true,
      duration: 1000,
      destination: {
        latitude: 23.10229,
        longitude: 113.3345211,
      },
      animationEnd() {
        console.log('animation end')
      }
    })
  },
  
  //显示选项框
  showModal: function() {
    wx.navigateTo({
      url: '../modal/modal?code='+this.data.code,
    })
   
  },

})
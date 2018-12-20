//index.js
//获取应用实例
Page({
  data: {
  },
  onLoad:function(options){
   //接收数据
    var that = this
    var markers = JSON.parse(options.markers) 
    //var array = JSON.parse(options.array) 
    that.setData({
      markers:markers,
      //array:array
    })
    console.log(that.data.markers)
    that.mapCtx = wx.createMapContext('myMap')
    that.mapCtx.includePoints({
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
 



})
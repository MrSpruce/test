//index.js
//获取应用实例
Page({
  data: {
    cs: [{ "name": "runoob", "alexa": 10000, "site": "www.runoob.com" },
      { "name": "runoob", "alexa": 10000, "site": "www.runoob.com" }],
	  /*
    //double user #2
    latitude: 39.983790,
    longitude: 116.306872,
    startlati: 39.908772,
    startlongi: 116.397499,
    endlati: 39.934243,
    endlongi: 116.466518,
    //double user #1
    latitude: 39.983790,
    longitude: 116.306872,
    startlati: 39.989410,
    startlongi: 116.480881,
    endlati: 39.933442, 
    endlongi: 116.441081,
    */
    //single user debug data
    latitude: 39.983790,
    longitude: 116.306872,
    startlati: 39.983790,
    startlongi: 116.306872,
    endlati: 39.920552,
    endlongi: 116.433366,
    travelmode: 0,
    traveltag: ['Driving', 'Transit', 'Walking'],
    includepoints: [{

      latitude: 39.992870,
      longitude: 116.310250,

    },
    {

      latitude: 39.992115,
      longitude: 116.315734,

    }
    ],

    // markers: [{
    //   id: 1,
    //   latitude: 39.983900,
    //   longitude: 116.306910,
    //   label:'User 1',
    //   iconPath: "../../image/user1.jpg",
    //   width: 35,
    //   height: 45
    // },
    // {
    //   id: 2,
    //   latitude: 39.920629,
    //   longitude: 116.433149,
    //   label: 'User 2',
    //   iconPath: "../../image/user2.jpg",
    //   width: 35,
    //   height: 45
    // },
    //   {
    //     id: 3,
    //     latitude: 39.95933,
    //     longitude: 116.29845,
    //     label: 'User 3',
    //     iconPath: "../../image/user3.jpg",
    //     width: 35,
    //     height: 45
    //   },
    //   {
    //     id: 4,
    //     latitude: 39.94840869,
    //     longitude: 116.4147603,
    //     iconPath: "../../image/loca_selected.png",


    //   },
    //   {
    //     id: 5,
    //     latitude: 39.9576592,
    //     longitude: 116.3659024,
    //     label: 'User 3',

    //   },
    //   {
    //     id: 6,
    //     latitude: 39.9410178,
    //     longitude: 116.3528006,
    //     label: 'User 3',

    //   },
    //   {
    //     id: 7,
    //     latitude: 39.97081778,
    //     longitude: 116.3158664,
    //     label: 'User 3',

    //   }],

    time: '00:00', //默认起始时间  
    time2: '23:00', //默认结束时间 
    radioValues: [{
      'value': '驾车',
      'selected': true
    },
    {
      'value': '公交',
      'selected': false
    },
    {
      'value': '步行',
      'selected': false
    },
    ],
    pois: [{
      'name': '第一饭店',
      'address': '',
      'latitude': '',
      'longitude': '',
      'selected': true,
    },
    {
      'name': '第二饭店',
      'address': '',
      'latitude': '',
      'longitude': '',
      'selected': false
    },
    {
      'name': '第三饭店',
      'address': '',
      'latitude': '',
      'longitude': '',
      'selected': false
    },
    {
      'name': '第四饭店',
      'address': '',
      'latitude': '',
      'longitude': '',
      'selected': false
    },
    {
      'name': '第五饭店',
      'address': '',
      'latitude': '',
      'longitude': '',
      'selected': false
    },
    ],
    clazz1: ["selected", ""],
    selectIndex: 0,//0:聚餐,1:聚玩,2:联合办公   
    selsectState: [1, 0, 0], //聚餐or聚玩 
    jvcan_option: [0, 0, 0, 0, 0, 0], //聚餐选项
    jvwan_option: [0, 0, 0], //聚玩选项
    work_option: [0, 0, 0],
    place_type: [0, 0, 0, 0, 0, 0, 0], //商家风格

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
  setInitialTime: function () {
    var date = new Date();
    var hour = date.getHours().toString();
    var minute = date.getMinutes().toString();
    minute = minute[1] ? minute : '0' + minute
    this.setData({ time: hour + ':' + minute })
  },
  onLoad: function (e) {
    //接收code
    var code=e.code;
    this.setData({
      code:code
    })
    console.log(code)
    //弹出动画 
    var animation = wx.createAnimation({
      duration: 200,
      timingFunction: "linear",
      delay: 0
    })
    this.animation = animation
    animation.translateY(300).step()
    this.setData({
      animationData: animation.export(),
      showModalStatus: true,
      showActiveTypeStatus: false,
      showPOIStatus: false
    })
    setTimeout(function () {
      animation.translateY(0).step()
      this.setData({
        animationData: animation.export()
      })
    }.bind(this), 200)
  },
  onReady: function (e) {
    this.mapCtx = wx.createMapContext('myMap', this)
    this.getCenterLocation()
    this.mapCtx.moveToLocation()
    this.setInitialTime()
    // 开机定位
  },
  getCenterLocation: function () {
    this.mapCtx.getCenterLocation({
      success: function (res) {

        console.log(res.longitude)
        console.log(res.latitude)

      }
    })
  },
  moveToLocation: function () {
    this.mapCtx.moveToLocation()
    // 定位
  },
  translateMarker: function () {
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

  showActiveType: function () {
    // 显示遮罩层 活动类型
    var animation = wx.createAnimation({
      duration: 200,
      timingFunction: "linear",
      delay: 0
    })
    this.animation = animation
    animation.translateY(300).step()
    this.setData({
      animationData: animation.export(),
      showActiveTypeStatus: true,
      showModalStatus: false,
      showPOIStatus: false
    })
    setTimeout(function () {
      animation.translateY(0).step()
      this.setData({
        animationData: animation.export()
      })
    }.bind(this), 200)
  },
  showPOI: function () {
    // 显示遮罩层 活动类型
    var animation = wx.createAnimation({
      duration: 200,
      timingFunction: "linear",
      delay: 0
    })
    this.animation = animation
    animation.translateY(300).step()
    this.setData({
      animationData: animation.export(),
      showActiveTypeStatus: false,
      showModalStatus: false,
      showPOIStatus: true
    })
    setTimeout(function () {
      animation.translateY(0).step()
      this.setData({
        animationData: animation.export()
      })
    }.bind(this), 200)
  },
  //显示选项框
  showModal: function () {
    //显示遮罩层
    // var animation = wx.createAnimation({
    //   duration: 200,
    //   timingFunction: "linear",
    //   delay: 0
    // })
    // this.animation = animation
    // animation.translateY(300).step()
    // this.setData({
    //   animationData: animation.export(),
    //   showModalStatus: true,
    //   showActiveTypeStatus: false,
    //   showPOIStatus: false
    // })
    // setTimeout(function () {
    //   animation.translateY(0).step()
    //   this.setData({
    //     animationData: animation.export()
    //   })
    // }.bind(this), 200)
  },
  //隐藏对话框
  hideModal: function () {
    // 隐藏遮罩层
    var animation = wx.createAnimation({
      duration: 200,
      timingFunction: "linear",
      delay: 0
    })
    this.animation = animation
    animation.translateY(300).step()
    this.setData({
      animationData: animation.export(),
    })
    setTimeout(function () {
      animation.translateY(0).step()
      this.setData({
        animationData: animation.export(),
        showModalStatus: false,
        showActiveTypeStatus: false,
        showPOIStatus: false
      })
    }.bind(this), 200)
  },

  // 时间段选择  
  bindDateChange(e) {
    let that = this;
    console.log(e.detail.value)
    that.setData({
      time: e.detail.value,
    })
  },
  bindDateChange2(e) {
    let that = this;
    that.setData({
      time2: e.detail.value,
    })
  },

  indexChanged: function (e) {
    // 点中的是组中第个元素
    var index = e.target.dataset.index;
    // 读取原始的数组
    var radioValues = this.data.radioValues;
    for (var i = 0; i < radioValues.length; i++) {
      // 全部改为非选中
      radioValues[i].selected = false;
    }
    // 当前那个改为选中
    radioValues[index].selected = true;

    // 写回数据
    this.setData({
      radioValues: radioValues
    });
    // clazz状态
    this.clazzStatus();

  },
  clazzStatus: function () {
    /* 此方法分别被加载时调用，点击某段时调用 */
    // class样式表如"selected last","selected"
    var clazz = [];
    // 参照数据源
    var radioValues = this.data.radioValues;
    for (var i = 0; i < radioValues.length; i++) {
      // 默认为空串，即普通按钮
      var cls = '';
      // 高亮，追回selected
      if (radioValues[i].selected) {
        cls += 'selected ';
      }
      // 最后个元素, 追加last
      if (i == radioValues.length - 1) {
        cls += 'last ';
      }
      //去掉尾部空格
      cls = cls.replace(/(\s*$)/g, '');
      clazz[i] = cls;
    }
    // 写回数据
    this.setData({
      clazz: clazz
    });

  },
  clickplace_type: function (e) {
    // 商家风格
    var place = e.currentTarget.dataset.place;
    var place_type = this.data.place_type;
    if (place_type[place] == 0) place_type[place] = 1;
    else place_type[place] = 0;
    this.setData({
      place_type: place_type,
      place_selected: place
    });
  },
  clickjvcan_option: function (e) {
    //聚餐类型选择
    var jvcan = e.currentTarget.dataset.jvcan;

    var jvcan_option = this.data.jvcan_option;
    if (jvcan_option[jvcan] == 0) jvcan_option[jvcan] = 1;
    else jvcan_option[jvcan] = 0;

    this.setData({
      jvcan_option: jvcan_option,
    });
  },
  clickjvwan_option: function (e) {
    //聚玩类型选择
    var jvwan = e.currentTarget.dataset.jvwan;

    var jvwan_option = this.data.jvwan_option;
    if (jvwan_option[jvwan] == 0) jvwan_option[jvwan] = 1;
    else jvwan_option[jvwan] = 0;

    this.setData({
      jvwan_option: jvwan_option,
    });
  },
  clickwork_option: function (e) {
    //办公类型选择
    var work = e.currentTarget.dataset.work;

    var work_option = this.data.work_option;
    if (work_option[work] == 0) work_option[work] = 1;
    else work_option[work] = 0;

    this.setData({
      work_option: work_option,
    });
  },
  clickjvcan: function () {
    this.setData({
      selsectState: [1, 0, 0],
      selectIndex: 0
    });

  },

  clickjvwan: function () {
    this.setData({
      selsectState: [0, 1, 0],
      selectIndex: 1
    });

  },
  clickwork: function () {
    this.setData({
      selsectState: [0, 0, 1],
      selectIndex: 2
    });

  },
  //移动选点 聚前位置
  onChangeAddressBefore: function () {
    var _page = this;
    wx.chooseLocation({
      success: function (res) {
        _page.setData({
          chooseAddressBefore: res.name,
          startlati: res.latitude,
          startlongi: res.longitude
        });
        console.log('lat:', res.latitude)
        console.log('lon:', res.longitude)
      },
      fail: function (err) {
        console.log(err)
      }
    });
  },
  //聚后位置
  onChangeAddressAfter: function () {
    var _page = this;
    wx.chooseLocation({
      success: function (res) {
        _page.setData({
          chooseAddressAfter: res.name,
          endlati: res.latitude,
          endlongi: res.longitude
        });
      },
      fail: function (err) {
        console.log(err)
      }
    });
  },

  //从服务器收到反馈，并显示推荐结果
  recommendPOI: function (e) {
    var that=this;
    that.getServer();
    //var markers = JSON.stringify(that.data.markers)
    //var a=JSON.parse(markers)
    // console.log(that.data.markers)
    //  wx.navigateTo({
    //    url: '../map/map?markers=' + markers,
    //  })
    // this.includePoints()
     
    // this.setData({
    //   showPOIStatus: true
    // })
    // this.showPOI()
  },
  // 与服务器通信
  getServer: function () {
    var that = this;
    var markers;
    var array;
    wx.request({
      url: 'https://wechatware.rxxxxx.net:2880/calculate_final_result', //
      method: 'POST',
      dataType: 'json',
      data: {
        starttime: that.data.time,//开始时间
        endtime: that.data.time2,//结束时间
        startlati: that.data.startlati,//起始纬度
        startlongi: that.data.startlongi,//起始经度
        endlati: that.data.endlati,//结束纬度
        endlongi: that.data.endlongi,//结束经度
        travelmode: that.data.traveltag[that.data.travelmode],//出行方式
        acttype: that.data.selectIndex,//活动类型，0聚餐，1聚玩，2办公
        jvcan: that.data.jvcan_option,//聚餐选项, 
        jvwan: that.data.jvwan_option,//聚玩选项
        work: '联合办公',//办公选项
        place_type: that.data.place_type,//商家风格
        code:that.data.code//登陆凭证
      },
      header: {
        'content-type': 'application/json' // 默认值
      },
      success: function (res) {
        that.setData({
          array: res.data.results,  
          markers: [{
            id: 1,
            latitude: res.data.results[0].wgsy,
            longitude: res.data.results[0].wgsx,
            title: res.data.results[0].title,
            address: res.data.results[0].address.replace(/\ +/g, ""),
            // callout: {
            //   content: res.data.results[0].title,
            //   fontSize: 14,
            //   color: '#ffffff',
            //   bgColor: '#000000',
            //   padding: 8,
            //   display: 'BYCLICK',
            //   borderRadius: 4,
            //   boxShadow: '4px 8px 16px 0 rgba(0)'
            // },
            // label: {
            //   content: res.data.results[0].title,
            // },
            //iconPath: "../../image/user1.jpg",
            width: 35,
            height: 45
          },
          {
            latitude: res.data.results[1].wgsy,
            longitude: res.data.results[1].wgsx,
            title: res.data.results[1].title,
            address: res.data.results[1].address.substring(0,32),
            // label: {
            //   content: res.data.results[1].title
            // },
            //iconPath: "../../image/user1.jpg",
            width: 35,
            height: 45
          },
          {
            latitude: res.data.results[2].wgsy,
            longitude: res.data.results[2].wgsx,
            title: res.data.results[2].title,
            address: res.data.results[2].address,
            // label: {
            //   content: res.data.results[2].title
            // },
            //iconPath: "../../image/user1.jpg",
            width: 35,
            height: 45
          },
          {
            latitude: res.data.results[3].wgsy,
            longitude: res.data.results[3].wgsx,
            title: res.data.results[3].title,
            address: res.data.results[3].address.substring(0, 20),
            // label: {
            //   content: res.data.results[3].title,
            // },
            //iconPath: "../../image/user1.jpg",
            width: 35,
            height: 45


          },
          {
            latitude: res.data.results[4].wgsy,
            longitude: res.data.results[4].wgsx,
            title: res.data.results[4].title,
            address: res.data.results[4].address,
            // label: {
            //   content: res.data.results[4].title
            // },
            //iconPath: "../../image/user1.jpg",
            width: 35,
            height: 45

          }
          ],
        }),
        //   markers=JSON.stringify(that.data.markers);
        //跳转至推荐页，携带获取Json数据
         wx.navigateTo({
          url: '../map/map?markers=' +JSON.stringify(that.data.markers),
         })
        console.log(that.data.array)

      }
    })
    console.log("jvcan:" + that.data.jvcan_option.toString() + '\n' +
      "jvwan:" + that.data.jvwan_option.toString())
  },

  // dh0: function () {
  //   wx.openLocation({
  //     latitude: this.data.markers[0].latitude,
  //     longitude: this.data.markers[0].longitude,
  //     scale: 18,
  //     name: this.data.markers[0].title,

  //   })
  // }



})
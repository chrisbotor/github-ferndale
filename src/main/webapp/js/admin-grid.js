Ext.onReady(function(){

    Ext.BLANK_IMAGE_URL = '/homeportal/ext-3.4.0/resources/images/default/s.gif';
	
    var Owner = Ext.data.Record.create([
	{name: 'id'},
    {
        name: 'lastname',
        type: 'string'
    },
    {
        name: 'firstname',
        type: 'string'
    },
    {
        name: 'middlename',
        type: 'string'
    },{
        name: 'birthdate',
        type: 'string'
    },
    {
        name: 'civilStatus',
        type: 'string'
    },
    {
        name: 'mobileNumber',
        type: 'string'
    },
    {
        name: 'homeNumber',
        type: 'string'
    },
    {
        name: 'emailAddress',
        type: 'string'
    },
    {
        name: 'workName',
        type: 'string'
    },
    {
        name: 'workAddress',
        type: 'string'
    },
    {
        name: 'workLandline',
        type: 'string'
    },
    {
        name: 'workMobile',
        type: 'string'
    },
    {
        name: 'workEmail',
        type: 'string'
    },
    {
        name: 'status',
        type: 'string'
    },{
        name: 'fullName',
        type: 'string'
    }]);

   var House = Ext.data.Record.create([
        {
            name: 'id'
        },

        {
            name: 'owner',
            type: 'string'
        },
        {
            name: 'address',
            type: 'string'
        },
        {
            name: 'lotArea',
            type: 'string'
        },
        {
            name: 'rented',
            type: 'string'
        },
        {
            name: 'title',
            type: 'string'
        }]);

var Leesee = Ext.data.Record.create([
    {
        name: 'address',
        type: 'string'
    },
    {
        name: 'fullname',
        type: 'string'
    },
    {
        name: 'moveInDate',
        type: 'string'
    },
    {
        name: 'moveOutDate',
        type: 'string'
    }]);
    
    var ownerproxy = new Ext.data.HttpProxy({
        api: {
            read : 'owner/view.action',
            create : 'owner/create.action',
            update: 'owner/update.action',
            destroy: 'owner/delete.action'
        }
    });

    var houseproxy = new Ext.data.HttpProxy({
        api: {
            read : 'house/view.action',
            create : 'house/create.action',
            update: 'house/update.action',
            destroy: 'house/delete.action'
        }
    });

    var leeseeproxy = new Ext.data.HttpProxy({
        api: {
            read : 'leesee/view.action',
            create : 'leesee/create.action',
            update: 'leesee/update.action',
            destroy: 'leesee/delete.action'
        }
    });

    var ownerreader = new Ext.data.JsonReader({
        totalProperty: 'total',
        successProperty: 'success',
        idProperty: 'id',
        root: 'data',
        messageProperty: 'message'  // <-- New "messageProperty" meta-data
    },
    Owner);
    
    var housereader = new Ext.data.JsonReader({
        totalProperty: 'total',
        successProperty: 'success',
        idProperty: 'id',
        root: 'data',
        messageProperty: 'message'  // <-- New "messageProperty" meta-data
    }, 
    House);


    var leeseereader = new Ext.data.JsonReader({
        totalProperty: 'total',
        successProperty: 'success',
        idProperty: 'id',
        root: 'data',
        messageProperty: 'message'  // <-- New "messageProperty" meta-data
    },
    Leesee);

    // The new DataWriter component.
    var ownerwriter = new Ext.data.JsonWriter({
        encode: true,
        writeAllFields: true
    });

 // The new DataWriter component.
    var housewriter = new Ext.data.JsonWriter({
        encode: true,
        writeAllFields: true
    });

    // The new DataWriter component.
    var leeseewriter = new Ext.data.JsonWriter({
        encode: true,
        writeAllFields: true
    });
    
 // Typical Store collecting the Proxy, Reader and Writer together.
    var ownerstore = new Ext.data.Store({
        id: 'user',
        proxy: ownerproxy,
        reader: ownerreader,
        writer: ownerwriter,  // <-- plug a DataWriter into the store just as you would a Reader
        autoSave: false // <-- false would delay executing create, update, destroy requests until specifically told to do so with some [save] buton.
    });

    // Typical Store collecting the Proxy, Reader and Writer together.
    var housestore = new Ext.data.Store({
        id: 'user',
        proxy: houseproxy,
        reader: housereader,
        writer: housewriter,  // <-- plug a DataWriter into the store just as you would a Reader
        autoSave: false // <-- false would delay executing create, update, destroy requests until specifically told to do so with some [save] buton.
    });

    // Typical Store collecting the Proxy, Reader and Writer together.
    var leeseestore = new Ext.data.Store({
        id: 'user',
        proxy: leeseeproxy,
        reader: leeseereader,
        writer: leeseewriter,  // <-- plug a DataWriter into the store just as you would a Reader
        autoSave: false // <-- false would delay executing create, update, destroy requests until specifically told to do so with some [save] buton.
    });

    //read the data from simple array
    ownerstore.load();
    housestore.load();
    leeseestore.load();
    
    //Ext.data.DataProxy.addListener('exception', function(ownerproxy, type, action, options, res) {
    	//Ext.Msg.show({
    		//title: 'ERROR',
    		//msg: res.message,
    		//icon: Ext.MessageBox.ERROR,
    		//buttons: Ext.Msg.OK
    	//});
    //});

    
    var ownereditor = new Ext.ux.grid.RowEditor({
        saveText: 'Update'
    });

    var houseeditor = new Ext.ux.grid.RowEditor({
        saveText: 'Update'
    });

    var leeseeeditor = new Ext.ux.grid.RowEditor({
        saveText: 'Update'
    });
    
//create owner grid
var ownerGrid = new Ext.grid.GridPanel({
    store: ownerstore,
    columns: [
        {header: "LAST NAME",
         width: 100,
         sortable: true,
         dataIndex: 'lastname',
         editor: {
            xtype: 'textfield',
            allowBlank: false
        }},
        {header: "FIRST NAME",
         width:100,
         sortable: true,
         dataIndex: 'firstname',
        editor: {
            xtype: 'textfield',
            allowBlank: false
        }},
        {header: "MIDDLE NAME",
         width:100,
         sortable: true,
         dataIndex: 'middlename',
         editor: {
            xtype: 'textfield',
            allowBlank: false
        }},
        {header: "CIVIL STATUS",
         width:100,
         sortable: true,
         dataIndex: 'civilStatus',
         editor: {
            xtype: 'textfield',
            allowBlank: true
        }},
        {header: "MOBILE NUMBER",
         width:100,
         sortable: true,
         dataIndex: 'mobileNumber',
         editor: {
            xtype: 'textfield',
            allowBlank: true
        }},
        {header: "HOME NUMBER",
         width:100,
         sortable: true,
         dataIndex: 'homeNumber',
         editor: {
            xtype: 'textfield',
            allowBlank: true
        }},
        {header: "EMAIL ADDRESS",
         width:100,
         sortable: true,
         dataIndex: 'emailAddress',
         editor: {
            xtype: 'textfield',
            allowBlank: true
        }},
        {header: "WORK NAME",
         width:100,
         sortable: true,
         dataIndex: 'workName',
         editor: {
            xtype: 'textfield',
            allowBlank: true
        }},
        {header: "WORK ADDRESS",
         width:100,
         sortable: true,
         dataIndex: 'workAddress',
         editor: {
            xtype: 'textfield',
            allowBlank: true
        }},
        {header: "WORK LANDLINE",
         width:100,
         sortable: true,
         dataIndex: 'workLandline',
         editor: {
            xtype: 'textfield',
            allowBlank: true
        }},
        {header: "WORK MOBILE",
         width:100,
         sortable: true,
         dataIndex: 'workMobile',
         editor: {
            xtype: 'textfield',
            allowBlank: true
        }},
        {header: "WORK EMAIL",
         width: 100,
         sortable: true,
         dataIndex: 'workEmail',
         editor: {
            xtype: 'textfield',
            allowBlank: true
        }},
    {header: "STATUS",
                    width:100,
                    sortable: true,
                    dataIndex:'status',
                    editor: {
            xtype:'combo',
            queryMode: 'server',
                        store: new Ext.data.ArrayStore({
                               fields: ['abbr', 'action'],
                               data : [
                                       ['Active', 'Active'],
                                       ['In Active', 'In Active']
                                      ]
                                }),
                               displayField:'action',
                               valueField: 'abbr',
                               mode: 'local',
                              typeAhead: false,
                              triggerAction: 'all',
                              lazyRender: true,
                              emptyText: 'Select action'
        }}
    ],
     viewConfig: {
            forceFit:true
        },
    plugins: [ownereditor],
    title: 'OWNER',
    height: 500,
    width: 1200,
    frame:true,
    
    tbar: [{
        iconCls: 'icon-user-add',
        text: 'Add Owner',
        handler: function(){
            var e = new Owner({
                lastname: '',
                firstname: '',
                middlename: '',
                status: ''
            });
            ownereditor.stopEditing();
            ownerstore.insert(0, e);
            ownerGrid.getView().refresh();
            ownerGrid.getSelectionModel().selectRow(0);
            ownereditor.startEditing(0);
        }
    },{
        iconCls: 'icon-user-delete',
        text: 'Remove Owner',
        handler: function(){
            ownereditor.stopEditing();
            var s = ownerGrid.getSelectionModel().getSelections();
            for(var i = 0, r; r = s[i]; i++){
                ownerstore.remove(r);
            }
        }
    },{
        iconCls: 'icon-user-save',
        text: 'Save All Modifications',
        handler: function(){
            ownerstore.save();
        }
    }]
});

//var tpl = new Ext.XTemplate('<tpl for="."><div class="x-combo-list-item" >{firstname} {middlename} {lastname}</div></tpl>');
var fullnamecombo = new Ext.form.ComboBox({
  store: ownerstore,
  //tpl: '<tpl for="."><div class="x-combo-list-item">{firstname} {middlename} {lastname}</div></tpl>',

  id: 'fullName',
  valueField: 'fullName',
  displayField: 'fullName',
  selectOnFocus: true,
  mode: 'local',
  typeAhead: true,
  editable: false,
  triggerAction: 'all'
  
});

// create house grid
var houseGrid = new Ext.grid.GridPanel({
    store: housestore,
    columns: [
        {
         header: "ID",
         width: 50,
         hidden:true,
         sortable: true,
         dataIndex: 'id'
        },
        {
         header: "OWNER",
         width: 200,
         sortable: true,
         dataIndex: 'owner'
        },
        {
         header: "ADDRESS",
         width:300,
         sortable: true,
         dataIndex: 'address'
        },
        {
         header: "LOT AREA",
         width:100,
         sortable: true,
         dataIndex: 'lotArea'
         },
         {
         header: "TITLE",
         width:150,
         sortable: true,
         dataIndex: 'title'
         },
        {
         header: "RENTED",
         width:100,
         sortable: true,
         dataIndex: 'rented'
         }
    ],
     viewConfig: {
            forceFit:true
        },
    plugins: [houseeditor],
    title: 'HOUSE',
    height: 500,
    width: 1200,
    frame:true,
    tbar: [{
        iconCls: 'icon-user-save',
        text: 'Save All Modifications',
        handler: function(){
            housestore.save();
        }
    }]
});

var addresscombo = new Ext.form.ComboBox({
  store: housestore,
  //tpl: '<tpl for="."><div class="x-combo-list-item">{firstname} {middlename} {lastname}</div></tpl>',

  id: 'address',
  valueField: 'address',
  displayField: 'address',
  selectOnFocus: true,
  mode: 'local',
  typeAhead: true,
  editable: false,
  triggerAction: 'all'

});

//create owner grid
var leeseeGrid = new Ext.grid.GridPanel({
    store: leeseestore,
    columns: [
        {
        header: "ADDRESS",
         width: 200,
         sortable: true,
         dataIndex: 'address'
        },{
         header: "FULLNAME",
         width: 100,
         sortable: true,
         dataIndex: 'fullname'
         },{
         header: "MOVE IN DATE",
         width:100,
         sortable: true,
         dataIndex: 'moveInDate'
         },{
         header: "MOVE OUT DATE",
         width:100,
         sortable: true,
         dataIndex: 'moveOutDate'
         }
    ],
     viewConfig: {
            forceFit:true
        },
    //plugins: [leeseeeditor],
    title: 'LEESEE',
    height: 500,
    width: 1200,
    frame:true,
    tbar: [{
        iconCls: 'icon-user-add',
        text: 'Add Leesee',
        handler: function(){
            //var e = new Leesee({
                //address:'',
                //lastname: '',
                //firstname: '',
                //middlename: '',
                //civilStatus:'',
                //status:''
            //});
           // leeseeeditor.stopEditing();
            //leeseestore.insert(0, e);
            leeseeGrid.getView().refresh();
            leeseeGrid.getSelectionModel().selectRow(0);
            //leeseeeditor.startEditing(0);
        }
    }
    ,{
        iconCls: 'icon-user-delete',
        text: 'Remove Leesee',
        handler: function(){
            leeseeeditor.stopEditing();
            var s = leeseeGrid.getSelectionModel().getSelections();
            for(var i = 0, r; r = s[i]; i++){
                leeseestore.remove(r);
            }
        }
    }
    ,{
        iconCls: 'icon-user-save',
        text: 'Save All Modifications',
        handler: function(){
            leeseestore.save();
        }
    }]
});

/*var treeDivs = '<div id="amenity-tree-div"></div>' +
		'<div id="service-tree-div"></div>' +
		'<div id="employee-tree-div"></div>' +
		'<div id="vehicle-tree-div"></div>';*/

function renderTopics(value){
       return String.format(
                '<b>\n\
                <a style="color:red" href="home.action?t='+value+'">{0}</a>\n\
                </b>',
                value);
    }

var adminBorderPanel = new Ext.Panel({
	    renderTo: 'my-tabs',
	    width: 1200,
            height: 530,
	    title: 'Portal Admin Menu',
	    layout:'border',
	    defaults: {
	        collapsible: true,
	        split: true,
	        bodyStyle: 'padding:15px'
	    },
	    items: [/*{
	        title: 'Alerts List',
	        region:'west',
	        margins: '5 0 0 0',
	        cmargins: '5 5 0 0',
	        width: 300,
	        minSize: 100,
	        maxSize: 250,
	        html: treeDivs
	    },*/{
	        title: 'Main Content',
	        collapsible: false,
	        region:'center',
	        margins: '5 0 0 0',
	        xtype: 'tabpanel',
	        activeTab: 0, // index or id
	        items: [ownerGrid,houseGrid,leeseeGrid]
	    }]
	});

//Amenity Tree
	var amenityTree = new Ext.tree.TreePanel({
		xtype : 'treepanel',
		autoScroll : true,
		loader : new Ext.tree.TreeLoader({
				url : 'alert/amenity/view.action'
			}),
		root : {
			text : 'Amenity Requests',
			id : 'data',
			cls: 'folder',
			expanded : true
                        
		},
		renderTo: 'amenity-tree-div'
	});

//Service Tree
	var serviceTree = new Ext.tree.TreePanel({
		xtype : 'treepanel',
		autoScroll : true,
		loader : new Ext.tree.TreeLoader({
				url : 'alert/service/view.action'
			}),
		root : {
			text : 'Service Requests',
			id : 'data',
			cls: 'folder',
			expanded : true
                        
		},
                listeners: {
                  itemclick: function(view,rec,item,index,eventObj)
        {          
            alert(index);          
        }
               },
		renderTo: 'service-tree-div'
	});
        
        

//Employee Tree
	var employeeTree = new Ext.tree.TreePanel({
		xtype : 'treepanel',
		autoScroll : true,
                renderer:renderTopics,
		loader : new Ext.tree.TreeLoader({
				url : 'alert/employee/view.action'
			}),
		root : {
			text : 'Employee Updates',
			id : 'data',
			cls: 'folder',
			expanded : true
                        
		},
		renderTo: 'employee-tree-div'
	});

	//Vehicle Tree
	var vehicleTree = new Ext.tree.TreePanel({
		xtype : 'treepanel',
		autoScroll : true,
                renderer:renderTopics,
		loader : new Ext.tree.TreeLoader({
				url : 'alert/vehicle/view.action'
			}),
		root : {
			text : 'Vehicle Updates',
			id : 'data',
			cls: 'folder',
			expanded : true
                        
		},
		renderTo: 'vehicle-tree-div'
	});
    
});

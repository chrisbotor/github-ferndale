Ext.onReady(function(){
    
    //Ext.QuickTips.init();

    Ext.BLANK_IMAGE_URL = '/homeportal/ext-3.4.0/resources/images/default/s.gif';
    //var hId = Ext.String.format(document.getElementById("houseIds").value);
    
    // var params = window.location.search.substring(1);
    
   // var params = Ext.urlDecode(location.search.substring(1));
   // alert('PARAMS: ' + params);
   // alert('Biller User ID: ' + params.billerUserId);
    
    var billerUserId = Ext.get('billerUserId').getValue();
    // alert('billerUserId: ' + billerUserId);
    
    var billerHouseId = Ext.get('billerHouseId').getValue();
    // alert('billerHouseId: ' + billerHouseId);
    
    var billerPayeeName = Ext.get('billerPayeeName').getValue();
    // alert('billerPayeeName: ' + billerPayeeName);
    
    var billerModeOfPayment = Ext.get('billerModeOfPayment').getValue();
    // alert('billerModeOfPayment: ' + billerModeOfPayment);

    var billerPayeeAddress = Ext.get('billerPayeeAddress').getValue();
    // alert('billerPayeeAddress: ' + billerPayeeAddress);
    
    var billerBank = Ext.get('billerBank').getValue();
    // alert('billerBank: ' + billerBank);
    
    var billerChequeNumber = Ext.get('billerChequeNumber').getValue();
    // alert('billerChequeNumber: ' + billerChequeNumber);
    
    
    
    
   // var params = 'billerUserId=' + billerUserId + '&billerHouseId=' + billerHouseId + '&billerPayeeName=' + billerPayeeName + '&billerModeOfPayment=' + billerModeOfPayment;
    
    var params = 'billerUserId=' + billerUserId + '&billerHouseId=' + billerHouseId + '&billerPayeeName=' 
    								+ billerPayeeName + '&billerModeOfPayment=' + billerModeOfPayment + '&billerPayeeAddress=' + billerPayeeAddress
    								+ '&billerBank=' + billerBank + '&billerChequeNumber=' + billerChequeNumber; 
    
    var BillingBean = Ext.data.Record.create([
    {
        name: 'id',
        type: 'int'
    },
    {
        name: 'userId',
        type: 'int'
    },{
        name: 'requestId',
        type: 'int'
    },{
        name: 'orderTypeId',
        type: 'int'
    },{
        name: 'reference',
        type: 'string'
    },
    {
        name: 'description',
        type: 'string'
    },{
        name: 'payeeName',
        type: 'string'
    },
    {
        name: 'requestedDate',
        type: 'string'
    },{
        name: 'postedDate',
        type: 'string'
    },
    {
        name: 'amount',
        type: 'double'
    },{
        name: 'paidAmount',
        type: 'double'
    },{
        name: 'balance',
        type: 'double'
    },{
        name: 'amountToPay',
        type: 'double'
    }
    ]);


    var proxy = new Ext.data.HttpProxy({
        api: {
            read : 'admin/viewListBill.action?' + params,
            update: 'admin/payBill.action?' + params
        }
    });

    var reader = new Ext.data.JsonReader({
        totalProperty: 'total',
        successProperty: 'success',
        idProperty: 'id',
        root: 'data',
        messageProperty: 'message'  // <-- New "messageProperty" meta-data
    },
    BillingBean);
    
    // The new DataWriter component.
    var writer = new Ext.data.JsonWriter({
        encode: true,
        writeAllFields: true
    });
    

    // Typical Store collecting the Proxy, Reader and Writer together.
    var store = new Ext.data.Store({
        id: 'user',
        proxy: proxy,
        writer: writer,
        reader: reader,  // <-- plug a DataWriter into the store just as you would a Reader
        autoSave: false // <-- false would delay executing create, update, destroy requests until specifically told to do so with some [save] buton.
    });

    //read the data from simple array
    store.load();
    
   //  var loadMask = new Ext.LoadMask(grid, {msg:'Wait message'});
    
    // LISTENER
   //  Ext.data.DataProxy.addListener('write', function(proxy, type, action, //working
    Ext.data.DataProxy.addListener('write', function(proxy, type, action,
    		options, res) 
    {
    	// Ext.Msg.alert('Payment Posted');
    	
    	Ext.MessageBox.show({
            msg: 'Payment posted successfully.',
            width:210,
            buttons: Ext.Msg.OK,
            fn: function()
            {
               
                // alert('OK is clicked!');
                
                var url = 'main/download/billing/pdf.action?billerUserId=' + billerUserId + '&billerPayeeAddress=' + billerPayeeAddress;
                
                   window.open('main/download/billing/pdf.action?billerUserId=' + billerUserId + '&billerPayeeAddress=' + billerPayeeAddress, '_blank');
                
            	// window.open('main/download/billing/pdf.action?billerUserId=' + billerUserId + '&billerPayeeAddress=' + billerPayeeAddress, '_blank');
            	
            	/*var win = Ext.create('App.model.Window', {
                    title: 'PDF Content',
                    width: 400,
                    height: 600,
                    modal   : true,
                    closeAction: 'hide',
                    items: [{ 
                             xtype: 'component',
                             html : '<iframe src="C:\Users\Racquel\Desktop\extjs.pdf" width="100%" height="100%"></iframe>',
                          }]
                });
            	
            	win.show();*/
            	
            	/*var win = new Ext.Window({
            		columnWidth: 0.35,
                    margin: '10 0 0 0',
                    xtype: 'fieldset',
            	    height : 530,
        	    	width : 600,
        	    	modal : true, // 1
        	    	title : 'Reservation details',
        	    	// html : 'Try to move or resize me. I dare you.',
        	    	plain : true,
        	    	border : false,
        	    	resizable : false, // 2
        	    	draggable : false, // 3
        	    	closable : true, // 4
        	    	buttonAlign : 'center',
        	    	defaultType: 'textfield',
        	    	items: [{ 
                        xtype: 'component',
                        html : '<iframe src=\"' + url + '\" width="100%" height="100%"></iframe>',
                     }]
        	    	 items: [
        	    	         login
        	    	       ]
                });
        	   
            
        	win.show();*/
            }
        });
    	
    	
    	grid.getStore().reload();
    	
    	/*if (type == 'response') {
    		var success = Ext.util.JSON.decode(res.responseText).success;
    	
    		if (success) {
    			Ext.Msg.alert('Payment Posted');
    		} 
    		else 
    		{
    			console.log('UPDATE FAILED');
    		}
    	}*/
    	
    });
    
    
    //Ext.data.DataProxy.addListener('exception', function(proxy, type, action, options, res) {
        //Ext.Msg.show({
            //title: 'ERROR',
            //msg: res.responseText,
            //icon: Ext.MessageBox.ERROR,
            //buttons: Ext.Msg.OK
        //});
    //});

    
    // var total = 0;
    var total = parseFloat(Ext.get('totalPayment').getValue());
    var totalChanged = parseFloat(Ext.get('totalPaymentChanged').getValue());
    
    // var totalChanged = 0;
    

    
    var editor = new Ext.ux.grid.RowEditor({
        saveText: 'Update',
        listeners: {
        	validateedit: function(obj, changes, rec, index) {
        	
        		var home = grid.getStore().getAt(index);
        		var fieldName = grid.getColumnModel().getDataIndex(12);
        		
        		var oldValue = rec.get(fieldName);
        		
        		// alert('old value: ' + oldValue);
        		
        		totalChanged = totalChanged + oldValue;
        		
        		/*var oldValue = 0;
        		
        		
        		if (rec.get(fieldName) != null)
        		{
        			oldValue = rec.get(fieldName);
        			
        			alert('old value: ' + oldValue)
        		}
        		
        		// alert("DATA: " + data);
        		
        		totalChanged = totalChanged + oldValue;
        		
        		alert("TOTAL CHANGED: " + totalChanged);*/
        		
        	},
        	afteredit: function(thisEditor, obj, rec, index) {  // racs
        		
        		var newValue = rec.get('amountToPay');
        		
        		// alert('new value: ' + newValue);
        		
        		/*if (rec.get('amountToPay') != null) {
	        		
        			newValue = rec.get('amountToPay');
        			
        			alert('new value: ' + newValue);
	        	}*/
        		
        		// total += rec.get('amountToPay') || 0;
        		
        		total = total + newValue;
        		
        		// alert("FINAL TOTAL: " + total);
        		
        	}
        }
    });
    
    
    var grid = new Ext.grid.GridPanel({
        store: store,
        id:'grid',
        columns: [
            {
                header: "JOB ORDER ID",
                width: 160,
                sortable: true,
                dataIndex: 'id',
                 hidden:true
            },
            {
                header: "USER ID",
                width: 160,
                sortable: true,
                dataIndex: 'userId',
                 hidden:true
            },{
                header: "REQUEST ID",
                width: 160,
                sortable: true,
                dataIndex: 'requestId',
                 hidden:true
            },{
                header: "ORDER TYPE ID",
                width: 160,
                sortable: true,
                dataIndex: 'orderTypeId',
                hidden:true
            },
            {
                header: "REFERENCE NUMBER",
                width: 160,
                sortable: true,
                dataIndex: 'reference'
            },{
                header: "PAYEE NAME",
                width: 160,
                sortable: true,
                dataIndex: 'payeeName'
            },
            {
                header: "DESCRIPTION",
                width: 250,
                sortable: true,
                dataIndex: 'description'
            },
            {
                header: "REQUESTED DATE",
                width: 120,
                sortable: true,
                dataIndex: 'requestedDate'
            },{
                header: "POSTED DATE",
                width: 120,
                sortable: true,
                dataIndex: 'postedDate'
            },
            {
                header: "AMOUNT",
                width: 100,
                sortable: true,
                dataIndex: 'amount'
            },{
                header: "PAID AMOUNT",
                width: 100,
                sortable: true,
                dataIndex: 'paidAmount'
            },{
                header: "BALANCE",
                width: 100,
                sortable: true,
                dataIndex: 'balance'
            },{
                header: "AMOUNT TO PAY",
                width: 100,
                id:'testko',
                name: 'racquel',
                sortable: true,
                dataIndex: 'amountToPay',
                editor:{
                    xtype: 'numberfield'
                }
            }
        ],
        viewConfig: {
            forceFit:true
        },
        plugins: [editor],
        title: 'BILLING DETAILS',
        frame:true,
        cls:'x-panel-blue',
        width:400,
        height:300,
        tbar: [{
            iconCls: 'icon-user-save',
            text: 'Save All Modifications',
            handler: function(){
            	
            	// alert('TOTAL: ' + total);
            	// alert('CHANGED: ' + totalChanged);
            	
            	Ext.MessageBox.confirm('Confirm', 'You are about to pay Php' + (total - totalChanged) + '. <br /> Continue?', submitPayment);
            	
            }
        },{
            iconCls: 'icon-user-add',
            text: 'Reload Billing details',
            handler: function(){
                Ext.getCmp('grid').getStore().load()
            }
        }]
    });
    
    
    var myTabPanel = new Ext.TabPanel({
        title:"MY TAB",
        renderTo: 'my-tabs',
        width: 1200,
            height: 530,
        activeItem: 0,
        items: [grid]
    });
    
    
   
    
    function submitPayment(btn)
    {
    	if (btn=='yes'){
    		
    		/*var myMask = new Ext.LoadMask(myPanel, {msg:"Submitting payment. Please wait..."});
    		myMask.show();*/
    		
    		store.save();
    		
    		// clear out the totals
        	total = 0;
        	totalChanged = 0;
    	}
    }

});

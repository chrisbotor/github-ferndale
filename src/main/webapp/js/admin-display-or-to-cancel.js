Ext.onReady(function()
{
	// alert('in admin-display-or-to-cancel.js!');

    //Ext.QuickTips.init();

    Ext.BLANK_IMAGE_URL = '/homeportal/ext-3.4.0/resources/images/default/s.gif';
    //var hId = Ext.String.format(document.getElementById("houseIds").value);

    // GLOBAL varible that holds the cancelled OR number
    var cancelledOr = "";
   
    
    // OfficialReceipt ExtJs object that maps to OfficialReceipt java model object
    var OfficialReceipt = Ext.data.Record.create(
    					  [
    					   	{	name: 'id', type: 'int' },
    					   	{	name: 'userId', type: 'int' },
    					   	{	name: 'houseId', type: 'int' },
    					   	{	name: 'orNumber', type: 'int' },
    					   	{	name: 'status', type: 'string' }
                          ]);


    
    var proxy = new Ext.data.HttpProxy({
        api: {
            read : 'admin-display-or.action'  // this should be the same as 'user-display-or.action' need to optimize this code
        }
    });

    
    
    var reader = new Ext.data.JsonReader({
        totalProperty: 'total',
        successProperty: 'success',
        idProperty: 'id',
        root: 'data',
        messageProperty: 'message'  // <-- New "messageProperty" meta-data
    },
    OfficialReceipt);

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

    Ext.data.DataProxy.addListener('exception', function(proxy, type, action, options, res) {
        Ext.Msg.show({
            title: 'ERROR',
            msg: res.responseText,
            icon: Ext.MessageBox.ERROR,
            buttons: Ext.Msg.OK
        });
    });



    var grid = new Ext.grid.GridPanel({
        store: store,
        id:'grid',
        name:'grid',
        columns: [
            {
                header: "OR NUMBER",
                width: 160,
                sortable: true,
                dataIndex: 'orNumber'
            },{
                header: "DOWNLOAD OR",
                dataIndex: 'orNumber',
                renderer : renderTopic

            },{
            	header: "CLICK TO CANCEL OR",
                dataIndex: 'orNumber',
                renderer : fnRenderCancelLink,
                width: 55
            }
        ], 
        listeners:{
        	cellclick:function (grid, rowIndex, columnIndex, e){
	            var colHeader = grid.getColumnModel().getColumnHeader(columnIndex);
	            
	            if (colHeader == "CLICK TO CANCEL OR")
	            {
	            	// set the value to empty String before setting again to what is selected
	            	cancelledOr = "";
	            	
	            	var record = grid.getStore().getAt(rowIndex);
	                var orNumber = record.get('orNumber');
	                
	                var status = record.get('status');
	                // alert('status: ' + status);
	                
	                if (status == 'F')
	                {
	                	Ext.Msg.alert('INFO', 'This OR has already been cancelled. <br /> You may only download this OR.');
	                }
	                else
	                {
	                	cancelledOr = orNumber;  // TODO: should add this OR number to a list in the future
		                
		                // TODO: Should be able to do bulk cancel in the future
		                Ext.MessageBox.confirm('Confirm', 'Would you like to cancel OR number <b>' + orNumber + '</b> ?', fnCancelOR);
	                }
	            }
	        }
        },
        viewConfig: {
            forceFit:true
        },

        title: 'LAST 10 OFFICIAL RECEIPT DETAILS',
        frame:true,
        cls:'x-panel-blue',
        width:400,
        height:300/*, // TODO: This should be used in the future to do BULK Cancel of OR
        tbar: [{
            iconCls: 'icon-user-save',
            text: 'Save All Modifications',
            handler: function()
            {
            	alert('Saving modifications...');
            	
            	var record = Ext.ComponentQuery.query('grid[name=grid]')[0].getSelectionModel().getSelection();
            	
            	alert('record: ' + record);
            	
            	var s = this.getSelectionModel().getSelection();
            	// And then you can iterate over the selected items, e.g.: 
            	selected = [];
            	Ext.each(s, function (item) {
            	  selected.push(item.data.someField);
            	});
            	
            	alert('selected: ' + selected);
            	
                store.save();
            }
        }]*/
    });


    var myTabPanel = new Ext.TabPanel({
        title:"MY TAB",
        renderTo: 'my-tabs',
        width: 1200,
        height: 510,
        activeItem: 0,
        items: [grid]
    });
    
    
  
    // FUNCTION THAT WILL FIRE IF CANCEL OR BUTTON WAS CLICKED
    function fnCancelOR(btn)
    {
    	if (btn=='yes')
    	{
    		// alert('cancelling ' + cancelledOr + '...');
    		
    		/*var myMask = new Ext.LoadMask(myTabPanel, {msg:"'cancelling " + cancelledOr + ". Please wait..."});
    		myMask.show();*/
    		
    		var orNum = cancelledOr;
    		
    		Ext.Ajax.request({
    		    url: 'admin-cancel-or.action?',
    		    method: 'POST',          
    		    params: {
    		    	orNumber: orNum
    		    },
    		    success: function()
    		    		 {
    		    			Ext.Msg.alert('INFO', 'OR ' + orNum + ' has been cancelled.');
    		    			location.reload(true);
    		    		 },                                    
    		    failure: function()
    		    		 {
    		    			console.log('failure');
    		    		 }
    		});
    	}
    }
    

});



// #####################################################  CUSTOM FUNCTIONS (RENDERERS)  ##########################################################
function renderTopic(value, p, record)
{	
	return String.format(
	        '<b>\n\
	                <a style="color:red" href="download/user-or-history-pdf.action?requestorUserId={0}&requestorHouseId={1}&orNumber={2}" target="_blank">\n\
	            	<b><img src="/homeportal/images/pdf.gif" alt="HTML tutorial" width="20" height="20"></b></a>\n\
	                </b>',
	         record.get('userId'),record.get('houseId'),value);
}


function fnRenderCancelLink(value, p, record)
{
	return String.format(
        '<b><a href="#" id="cancel">Cancel</a></b>',
        value,record.orNum,record.payee);
}



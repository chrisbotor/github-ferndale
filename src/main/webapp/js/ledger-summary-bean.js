
    // alert('in ledger-summary-bean.js!');
    
    var params = Ext.urlDecode(location.search.substring(1));
    var houseId = params.houseId;
    
    // alert('houseId: ' + houseId);



//##############################################		LEDGER SUMMARY BEAN 		####################################### //
    
    var LedgerSummaryBean = Ext.data.Record.create(
	[
	 	{ name: 'id', type: 'int' },
	 	{ name: 'userId', type: 'int' },
		{ name: 'houseId', type: 'int' },
		{ name: 'amount', type: 'double' },
		{ name: 'penalty', type: 'double' },
		{ name: 'createdAt', type: 'string' },
        { name: 'updatedAt', type: 'string' }
	]);

    
    
 // #######################		LEDGER SUMMARY BEAN PROXY		########################## //
        
        var ledgerSummaryBeanProxy = new Ext.data.HttpProxy({
            api: {
                read: 'user/get-ledger-summary.action?houseId=' + houseId
                //update  : 'owner/update-owner.action'
            }
        });
        
        
        
    
 // #######################		LEDGER SUMMARY BEAN READER		########################## //
    
    var ledgerSummaryBeanReader = new Ext.data.JsonReader({
        totalProperty: 'total',
        successProperty: 'success',
        idProperty: 'id',
        root: 'data',
        messageProperty: 'message'
      },
      	LedgerSummaryBean);
  
  
 
       
 
 // #######################		LEDGER SUMMARY BEAN STORE		########################## //
    
    var ledgerSummaryBeanStore = new Ext.data.Store({
        id: 'ledgerSummaryBeanStore',
        proxy: ledgerSummaryBeanProxy,
        reader: ledgerSummaryBeanReader,
        //writer: serwriter,  // <-- plug a DataWriter into the store just as you would a Reader
        autoSave: false
    });
    
 
    
    

 // #######################		IMPORTANT! OTHERWISE, THE PROXY WILL NOT BE DETECTED		########################## //
    
    //read the data from simple array
    // ledgerSummaryBeanStore.load();
    
    ledgerSummaryBeanStore.load({
        callback: function(records, operation, success) {
          
	        if(success)
	        {
	        	//alert('YES');
	        	
	        	var data = this.reader.jsonData.data;
	        	
	        	if (data != null)
	        	{
	        		var penalty = data.penalty;
	        		
	        		if (penalty > 0.00)
		        	{
		        		// var msg = Ext.Msg.alert('NOTICE', 'Your current total penalty is: <b>Php' + penalty.format(2) + '</b>');
			        	// msg.getDialog().tools.close.hide();
	        			// this removeXButton function is now part of common-util.js
	        			removeXButton(Ext.Msg.alert('NOTICE', 'Your current total penalty is: <b>Php' + penalty.format(2) + '</b>'));
		        	}
	        	}
	        	
	        	
	        	/*var penalty = this.reader.jsonData.data.penalty;
	        	
	        	if (penalty > 0.00)
	        	{
	        		// alert('PENALTY: ' + penalty);
	        		var msg = Ext.Msg.alert('NOTICE', 'Your current total penalty is: <b>Php' + penalty.format(2) + '</b>');
		        	msg.getDialog().tools.close.hide();
	        	}*/
	        }
        }
      });
    
    
    // TO FORMAT MONEY
    Number.prototype.format = function(n, x) {
        var re = '\\d(?=(\\d{' + (x || 3) + '})+' + (n > 0 ? '\\.' : '$') + ')';
        return this.toFixed(Math.max(0, ~~n)).replace(new RegExp(re, 'g'), '$&,');
    };
    
 
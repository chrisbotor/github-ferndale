Ext.onReady(function(){

    //Ext.QuickTips.init();

    Ext.BLANK_IMAGE_URL = '/homeportal/ext-3.4.0/resources/images/default/s.gif';
    //var hId = Ext.String.format(document.getElementById("houseIds").value);

    /*var BillingBean = Ext.data.Record.create([
   {
        name: 'id',
        type: 'int'
    },{
       name: 'orNumber',
       type: 'int'
    }
    ]);*/
    
    
    var OfficialReceipt = Ext.data.Record.create([
                          {
                        	  	name: 'id',
                                type: 'int'
                          },
                          {
                        	  name: 'orNumber',
                        	  type: 'int'
                          }
                          ]);


    
    var proxy = new Ext.data.HttpProxy({
        api: {
            read : 'user-display-or.action'
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

            },
        ], viewConfig: {
            forceFit:true
        },

        title: 'LAST 10 OFFICIAL RECEIPT DETAILS',
        frame:true,
        cls:'x-panel-blue',
        width:400,
        height:300
    });


    var myTabPanel = new Ext.TabPanel({
        title:"MY TAB",
        renderTo: 'my-tabs',
        width: 1230,
        height: 445,
        activeItem: 0,
        items: [grid]
    });

});

function renderTopic(value, p, record){

    return String.format(
        '<b>\n\
                <a style="color:red" href="download/user-or-history-pdf.action?orNumber={0}" target="_blank">\n\
            <b><img src="/homeportal/images/pdf.gif" alt="HTML tutorial" width="20" height="20"></b></a>\n\
                </b>',
        value,record.orNum,record.payee);
}

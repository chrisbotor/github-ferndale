Ext.onReady(function(){

    Ext.BLANK_IMAGE_URL = '/homeportal/ext-3.4.0/resources/images/default/s.gif';
    

    var ServiceBean = Ext.data.Record.create([
    {
        name: 'id',
        type: 'int'
    },
    {
        name: 'requestId',
        type: 'string'
    },
    {
        name: 'address',
        type: 'string'
    },{
        name: 'houseId',
        type: 'int'
    },{
        name: 'serviceId',
        type: 'int'
    },
    {
        name: 'des',
        type: 'string'
    },
    {
        name: 'preferredDate',
        type: 'string'
    },{
        name: 'preferredTime',
        type: 'string'
    }, {
        name: 'confirmedDate',
        type: 'string'
    }, {
        name: 'status',
        type: 'string'
    },{
        name: 'basic_cost',
        type: 'float'
    },{
        name: 'additional_cost',
        type: 'float'
    },{
        name: 'total_cost',
        type: 'float'
    },{
        name: 'uom',
        type: 'string'
    },{
        name: 'requestor',
        type: 'string'
    },{
        name: 'regularPrice',
        type: 'float'
    },{
        name: 'excessPrice',
        type: 'float'
    },{
        name: 'quantity',
        type: 'int'
    },{
        name: 'maxRegular',
        type: 'int'
    },{
        name: 'remarks',
        type: 'string'
    },{
        name: 'otherCharges',
        type: 'float'
    },{
        name: 'payeeName',
        type: 'string'
    },]);

    

    var srproxy = new Ext.data.HttpProxy({
        api: {
            read : 'admin/services.action'
        }
    });

    var srreader = new Ext.data.JsonReader({
        totalProperty: 'total',
        successProperty: 'success',
        idProperty: 'id',
        root: 'data',
        messageProperty: 'message'  // <-- New "messageProperty" meta-data
    },
    ServiceBean);

    // The new DataWriter component.
    var writer = new Ext.data.JsonWriter({
        encode: true,
        writeAllFields: true
    });
    

    // Typical Store collecting the Proxy, Reader and Writer together.
    var srstore = new Ext.data.Store({
        id: 'user',
        proxy: srproxy,
        reader: srreader,
        writer: writer,// <-- plug a DataWriter into the store just as you would a Reader
        autoSave: false // <-- false would delay executing create, update, destroy requests until specifically told to do so with some [save] buton.
    });
    
   
    srstore.load();
    
    
    var srgrid = new Ext.FormPanel({
        frame: true,
        title: 'SERVICE REQUEST',
        bodyPadding: 5,
        width: 700,
        id: 'service-form',
        layout: 'column',    // Specifies that the items will now be arranged in columns

        fieldDefaults: {
            //labelAlign: 'left',
            msgTarget: 'side'
        },
        
        items: [{
            columnWidth: 0.65,
            xtype: 'grid',
            id:'ars',
            store: srstore,
            cls:'x-panel-blue',
            width: 400,
            height:400,
            title:'SERVICE REQUEST',
            
            columns: [
            {
                header: "STATUS",
                width:100,
                sortable: true,
                dataIndex:'status',
                renderer:customRenderer
            },{
                header: "REQUEST ID",
                width:100,
                sortable: true,
                dataIndex:'requestId'
            },
            {
                header: "ID",
                width: 170,
                hidden:true,
                sortable: true,
                dataIndex: 'id'
            },
            {
                header: "HOUSE ID",
                width: 170,
                hidden:true,
                sortable: true,
                dataIndex: 'houseId'
            },{
                header: "UOM",
                width: 170,
                hidden:true,
                sortable: true,
                dataIndex: 'uom'
            },{
                header: "REGULAR PRICE",
                width: 170,
                hidden:true,
                sortable: true,
                dataIndex: 'regularPrice'
            },{
                header: "EXCESS PRICE",
                width: 170,
                hidden:true,
                sortable: true,
                dataIndex: 'excessPrice'
            },{
                header: "MAX REGULAR",
                width: 170,
                hidden:true,
                sortable: true,
                dataIndex: 'maxRegular'
            },
            {
                header: "ADDRESS",
                width: 200,
                sortable: true,
                dataIndex: 'address'
            },
            {
                header: "SERVICE",
                width: 170,
                sortable: true,
                dataIndex: 'des'
            },{
                header: "REQUESTOR",
                width: 170,
                sortable: true,
                dataIndex: 'requestor'
            },
            {
                header: "PAYEE NAME",
                width: 170,
                sortable: true,
                dataIndex: 'payeeName'
            },
            {
                header: "PREFFERED DATE",
                width: 170,
                sortable: true,
                dataIndex: 'preferredDate'
            },
            {
                header: "PREFERRED TIME",
                width: 170,
                sortable: true,
                dataIndex: 'preferredTime'
            },{
                header: "CONFIRMED DATE",
                width: 170,
                sortable: true,
                dataIndex: 'confirmedDate'
            },{
                header: "QUANTITY",
                width: 170,
                sortable: true,
                dataIndex: 'quantity'
            },
            {
                header: "BASIC COST",
                width: 170,
                sortable: true,
                dataIndex: 'basic_cost'
            }, {
                header: "ADDITIONAL COST",
                width: 170,
                sortable: true,
                dataIndex: 'additional_cost'
            },{
                header: "OTHER CHARGES",
                width: 170,
                sortable: true,
                dataIndex: 'otherCharges'
            },{
                header: "REMARKS",
                width: 170,
                sortable: true,
                dataIndex: 'remarks'
            },{
                header: "TOTAL COST",
                width: 170,
                sortable: true,
                dataIndex: 'total_cost'
            }
            ],
            listeners: {
                'rowclick':  function(grid,rowIndex,e){
                    var record = grid.getStore().getAt(rowIndex);
                    var id = record.get('id');
                    var requestId = record.get('requestId');
                    var status = record.get('status');
                    var address = record.get('address')
                    var requestor = record.get('requestor')
                    var payeeName = record.get('payeeName')
                    var service = record.get('des')
                    var preferredDate = record.get('preferredDate')
                    var preferredTime = record.get('preferredTime')
                    var confirmedDate = record.get('confirmedDate')
                    var quantity = record.get('quantity')
                    var basic_cost = record.get('basic_cost')
                    var additional_cost = record.get('additional_cost')
                    var total_cost = record.get('total_cost')
                    var uom = record.get('uom')
                    var regularPrice = record.get('regularPrice')
                    var excessPrice = record.get('excessPrice')
                    var maxRegular = record.get('maxRegular')
                    var remarks = record.get('remarks')
                    var otherCharges = record.get('otherCharges')
                    Ext.getCmp('ags-id').setValue(id)
                    Ext.getCmp('ags-requestId').setValue(requestId)
                    Ext.getCmp('ags-address').setValue(address)
                    Ext.getCmp('ags-requestor').setValue(requestor)
                    Ext.getCmp('ags-status').setValue(status)
                    Ext.getCmp('ags-service').setValue(service)
                    Ext.getCmp('ags-quantity').setValue(quantity)
                    Ext.getCmp('ags-preferredDate').setValue(preferredDate)
                    Ext.getCmp('ags-preferredTime').setValue(preferredTime)
                    Ext.getCmp('ags-confirmedDate').setValue(confirmedDate)
                    Ext.getCmp('ags-basic_cost').setValue(basic_cost)
                    Ext.getCmp('ags-additional_cost').setValue(additional_cost)
                    Ext.getCmp('ags-total_cost').setValue(total_cost)
                    Ext.getCmp('ags-uom').setValue(uom)
                    Ext.getCmp('ags-regularPrice').setValue(regularPrice)
                    Ext.getCmp('ags-excessPrice').setValue(excessPrice)
                    Ext.getCmp('ags-maxRegular').setValue(maxRegular)
                    Ext.getCmp('ags-remarks').setValue(remarks)
                    Ext.getCmp('ags-payeeName').setValue(payeeName)
                    Ext.getCmp('ags-otherCharges').setValue(otherCharges)
                    
                    if(requestor == 'ferndale admin'){
                        Ext.getCmp('ags-payeeName').show();
                    }else
                    {
                        Ext.getCmp('ags-payeeName').hide();
                    }
                    
                    if(status == 'New'){
                        Ext.getCmp('ags-basic_cost').hide();
                        Ext.getCmp('ags-additional_cost').hide();
                        Ext.getCmp('ags-otherCharges').hide();
                        Ext.getCmp('ags-remarks').hide();
                        Ext.getCmp('ags-total_cost').hide();
                    }
                    
                    if(status == 'Confirmed'){
                        Ext.getCmp('ags-basic_cost').show();
                        Ext.getCmp('ags-additional_cost').show();
                        Ext.getCmp('ags-otherCharges').show();
                        Ext.getCmp('ags-remarks').show();
                        Ext.getCmp('ags-total_cost').show()
                    }
                    
                    if(uom == 'pc' | uom == 'hph'){
                        Ext.getCmp('ags-quantity').show();
                    }
                    
                    if(uom == 'day'){
                        Ext.getCmp('ags-quantity').hide();
                    }
                }
            }
        },{
            columnWidth: 0.35,
            margin: '10 0 0 0',
            xtype: 'fieldset',
            title:'Service details',
            height:485,
            
            defaults: {
                width:150,
                labelWidth: 150,
                labelAlign:'right'
            },
            defaultType: 'textfield',
            items: [
            {
                fieldLabel: 'ID',
                name: 'id',
                id: 'ags-id',
                anchor: '85%',
                hidden:true
            },{
                fieldLabel: 'UOM',
                name: 'uom',
                id: 'ags-uom',
                anchor: '85%',
                hidden:true
            },{
                fieldLabel: 'MAX REGULAR',
                name: 'maxRegular',
                id: 'ags-maxRegular',
                anchor: '85%',
                hidden:true
            },{
                fieldLabel: 'REGULAR PRICE',
                name: 'regularPrice',
                id: 'ags-regularPrice',
                anchor: '85%',
                hidden:true
            },{
                fieldLabel: 'EXCESS PRICE',
                name: 'excessPrice',
                id: 'ags-excessPrice',
                anchor: '85%',
                hidden:true
            },
            {
                fieldLabel: 'STATUS',
                name: 'status',
                id:'ags-status',
                xtype:'combo',
                queryMode: 'server',
                store: new Ext.data.ArrayStore({
                    fields: ['abbr', 'action'],
                    data : [
                    ['Done', 'Done'],
                    ['Confirmed', 'Confirmed'],
                    ['Re-Scheduled', 'Re-Scheduled'],
                    ['Cancelled', 'Cancelled']
                    ]
                }),
                displayField:'action',
                valueField: 'abbr',
                mode: 'local',
                typeAhead: false,
                triggerAction: 'all',
                lazyRender: true,
                editable:false,
                emptyText: 'Select action',
                allowBlank: false,
                blankText: 'Status should not be null',
                anchor: '85%',
                listeners:{
                    select: function(field, newValue, oldValue){
                        
                         var limit = Ext.getCmp('ags-maxRegular').getValue()
                         var qty = Ext.getCmp('ags-quantity').getValue()
                         
                         if(field.value == "Confirmed"){
                            Ext.getCmp('ags-basic_cost').show();
                            Ext.getCmp('ags-additional_cost').show();
                            Ext.getCmp('ags-otherCharges').show();
                            Ext.getCmp('ags-remarks').show();
                            Ext.getCmp('ags-total_cost').show();
                            
                            if(Ext.getCmp('ags-uom').getValue() == 'hph'){
                                Ext.getCmp('ags-quantity').show();
                                
                                var basic_cost_hph = 0;
                                var additional_cost_hph = 0;
                                if(qty > limit){
                                    basic_cost_hph = Ext.getCmp('ags-regularPrice').getValue()
                                    var diff = qty - limit;
                                    additional_cost_hph = Ext.getCmp('ags-excessPrice').getValue() * diff
                                }else{
                                    basic_cost_hph = Ext.getCmp('ags-regularPrice').getValue() * qty
                                    additional_cost_hph = 0;
                                }
                                Ext.getCmp('ags-basic_cost').setValue(basic_cost_hph)
                                Ext.getCmp('ags-additional_cost').setValue(additional_cost_hph)
                            }
                            
                            
                            if(Ext.getCmp('ags-uom').getValue() == 'day'){
                                var basic_cost_day = Ext.getCmp('ags-regularPrice').getValue()
                                Ext.getCmp('ags-basic_cost').setValue(basic_cost_day)
                                
                            }
                            if(Ext.getCmp('ags-uom').getValue() == 'pc'){
                                var basic_cost_pc = 0;
                                var additional_cost_pc = 0;
                                if(qty > limit){
                                    basic_cost_pc = Ext.getCmp('ags-regularPrice').getValue()
                                    var diffs = qty - limit;
                                    additional_cost_pc = Ext.getCmp('ags-excessPrice').getValue() * diffs
                                }else{
                                    basic_cost_pc = Ext.getCmp('ags-regularPrice').getValue() * qty
                                    additional_cost_pc = 0;
                                }
                                Ext.getCmp('ags-basic_cost').setValue(basic_cost_pc)
                                Ext.getCmp('ags-additional_cost').setValue(additional_cost_pc)
                            }
                           
                         }
                         
                         if(field.value == "Done"){
                            Ext.getCmp('ags-basic_cost').show();
                            Ext.getCmp('ags-additional_cost').show();
                            Ext.getCmp('ags-remarks').show();
                            Ext.getCmp('ags-otherCharges').show();
                            Ext.getCmp('ags-total_cost').show();
                            
                            if(Ext.getCmp('ags-uom').getValue() == 'hph'){
                                Ext.getCmp('ags-quantity').show();
                                
                                var cbasic_cost_hph = 0.0;
                                var cadditional_cost_hph = 0.0;
                                var total_cost_hph = 0.0;
                                if(qty > limit){
                                    cbasic_cost_hph = Ext.getCmp('ags-regularPrice').getValue()
                                    var cdiff = qty - limit;
                                    cadditional_cost_hph = Ext.getCmp('ags-excessPrice').getValue() * cdiff
                                }else{
                                    cbasic_cost_hph = Ext.getCmp('ags-regularPrice').getValue() * qty
                                    cadditional_cost_hph = 0.0;
                                }
                                total_cost_hph = Number(Ext.getCmp('ags-basic_cost').getValue()) + Number(Ext.getCmp('ags-additional_cost').getValue()) + Number(Ext.getCmp('ags-otherCharges').getValue())
                                Ext.getCmp('ags-basic_cost').setValue(cbasic_cost_hph)
                                Ext.getCmp('ags-additional_cost').setValue(cadditional_cost_hph)
                                Ext.getCmp('ags-total_cost').setValue(total_cost_hph)
                            }
                            
                            
                            if(Ext.getCmp('ags-uom').getValue() == 'day'){
                                var ebasic_cost_day = Ext.getCmp('ags-regularPrice').getValue()
                                var cadditional_cost_day = Number(ebasic_cost_day) + Number(Ext.getCmp('ags-otherCharges').getValue())
                                Ext.getCmp('ags-basic_cost').setValue(ebasic_cost_day)
                                Ext.getCmp('ags-total_cost').setValue(cadditional_cost_day)
                                
                            }
                            if(Ext.getCmp('ags-uom').getValue() == 'pc'){
                                var dbasic_cost_pc = 0;
                                var dadditional_cost_pc = 0;
                                var total_cost_pc = 0.0;
                                if(qty > limit){
                                    dbasic_cost_pc = Ext.getCmp('ags-regularPrice').getValue()
                                    var ddiffs = qty - limit;
                                    dadditional_cost_pc = Ext.getCmp('ags-excessPrice').getValue() * ddiffs
                                    total_cost_pc = Number(dbasic_cost_pc) + Number(dadditional_cost_pc) + Number(Ext.getCmp('ags-otherCharges').getValue())
                                }else{
                                    dbasic_cost_pc = Ext.getCmp('ags-regularPrice').getValue() * qty
                                    dadditional_cost_pc = 0;
                                    total_cost_pc = Number(dbasic_cost_pc) + Number(dadditional_cost_pc) + Number(Ext.getCmp('ags-otherCharges').getValue())
                                }
                                
                                Ext.getCmp('ags-basic_cost').setValue(dbasic_cost_pc)
                                Ext.getCmp('ags-additional_cost').setValue(dadditional_cost_pc)
                                Ext.getCmp('ags-total_cost').setValue(total_cost_pc)
                            }
                           
                         }
                         
                         if(field.value == "Re-Scheduled" | field.value == "Cancelled"){
                            Ext.getCmp('ags-quantity').setValue(0)
                            Ext.getCmp('ags-basic_cost').setValue(0)
                            Ext.getCmp('ags-additional_cost').setValue(0)
                            Ext.getCmp('ags-otherCharges').setValue(0)
                            Ext.getCmp('ags-remarks').setValue("")
                            Ext.getCmp('ags-total_cost').setValue(0)
                            Ext.getCmp('ags-basic_cost').hide();
                            Ext.getCmp('ags-additional_cost').hide();
                            Ext.getCmp('ags-otherCharges').hide();
                            Ext.getCmp('ags-remarks').hide();
                            Ext.getCmp('ags-total_cost').hide();
                         }
                    }
                }
                
            },{
                fieldLabel: 'REQUEST ID',
                name: 'requestId',
                id: 'ags-requestId',
                anchor: '85%',
                readOnly:true
            },{
                fieldLabel: 'ADDRESS',
                name: 'address',
                id: 'ags-address',
                anchor: '85%',
                readOnly:true
            },{
                fieldLabel: 'REQUESTOR',
                name: 'requestor',
                id: 'ags-requestor',
                anchor: '85%',
                readOnly:true
            },{
                fieldLabel: 'PAYEE NAME',
                name: 'payeeName',
                id: 'ags-payeeName',
                anchor: '85%',
                readOnly:true,
                hidden:true
            },{
                fieldLabel: 'SERVICE',
                name: 'des',
                anchor: '85%',
                id: 'ags-service',
                readOnly:true
            },{
                fieldLabel: 'PREFFERED DATE',
                name: 'preferredDate',
                cls: 'cTextAlign', 
                readOnly:true,
                anchor: '85%',
                id: 'ags-preferredDate'
            },{
                fieldLabel: 'PREFERRED TIME',
                name: 'preferredDate',
                cls: 'cTextAlign', 
                readOnly:true,
                anchor: '85%',
                id: 'ags-preferredTime'
            },{
                fieldLabel: 'CONFIRMED DATE',
                name: 'confirmedDate',
                cls: 'cTextAlign',
                id: 'ags-confirmedDate',
                xtype: 'datefield',
                format: 'm/d/Y',
                submitFormat: 'm/d/Y',
                anchor: '85%'
            },{
                cls: 'cTextAlign',
                xtype: 'numberfield',
                fieldLabel: 'QUANTITY',
                name: 'quantity',
                anchor: '85%',
                allowBlank: false,
                id: 'ags-quantity',
                listeners: {
                'render': function(c) {
                c.getEl().on('keyup', function() {
                            var limit = Ext.getCmp('ags-maxRegular').getValue()
                            var qty = Ext.getCmp('ags-quantity').getValue()
                            Ext.getCmp('ags-basic_cost').show();
                            Ext.getCmp('ags-additional_cost').show();
                            Ext.getCmp('ags-remarks').show();
                            Ext.getCmp('ags-otherCharges').show();
                            Ext.getCmp('ags-total_cost').show();
                            
                            if(Ext.getCmp('ags-uom').getValue() == 'hph'){
                                Ext.getCmp('ags-quantity').show();
                                
                                var cbasic_cost_hph = 0.0;
                                var cadditional_cost_hph = 0.0;
                                var total_cost_hph = 0.0;
                                if(qty > limit){
                                    cbasic_cost_hph = Ext.getCmp('ags-regularPrice').getValue()
                                    var cdiff = qty - limit;
                                    cadditional_cost_hph = Ext.getCmp('ags-excessPrice').getValue() * cdiff
                                }else{
                                    cbasic_cost_hph = Ext.getCmp('ags-regularPrice').getValue() * qty
                                    cadditional_cost_hph = 0.0;
                                }
                                total_cost_hph = Number(Ext.getCmp('ags-basic_cost').getValue()) + Number(Ext.getCmp('ags-additional_cost').getValue()) + Number(Ext.getCmp('ags-otherCharges').getValue())
                                Ext.getCmp('ags-basic_cost').setValue(cbasic_cost_hph)
                                Ext.getCmp('ags-additional_cost').setValue(cadditional_cost_hph)
                                Ext.getCmp('ags-total_cost').setValue(total_cost_hph)
                            }
                            
                            
                            if(Ext.getCmp('ags-uom').getValue() == 'day'){
                                var ebasic_cost_day = Ext.getCmp('ags-regularPrice').getValue()
                                var cadditional_cost_day = Number(ebasic_cost_day) + Number(Ext.getCmp('ags-otherCharges').getValue())
                                Ext.getCmp('ags-basic_cost').setValue(ebasic_cost_day)
                                Ext.getCmp('ags-total_cost').setValue(cadditional_cost_day)
                                
                            }
                            if(Ext.getCmp('ags-uom').getValue() == 'pc'){
                                var dbasic_cost_pc = 0;
                                var dadditional_cost_pc = 0;
                                var total_cost_pc = 0.0;
                                if(qty > limit){
                                    dbasic_cost_pc = Ext.getCmp('ags-regularPrice').getValue()
                                    var ddiffs = qty - limit;
                                    dadditional_cost_pc = Ext.getCmp('ags-excessPrice').getValue() * ddiffs
                                    total_cost_pc = Number(dbasic_cost_pc) + Number(dadditional_cost_pc) + Number(Ext.getCmp('ags-otherCharges').getValue())
                                }else{
                                    dbasic_cost_pc = Ext.getCmp('ags-regularPrice').getValue() * qty
                                    dadditional_cost_pc = 0;
                                    total_cost_pc = Number(dbasic_cost_pc) + Number(dadditional_cost_pc) + Number(Ext.getCmp('ags-otherCharges').getValue())
                                }
                                
                                Ext.getCmp('ags-basic_cost').setValue(dbasic_cost_pc)
                                Ext.getCmp('ags-additional_cost').setValue(dadditional_cost_pc)
                                Ext.getCmp('ags-total_cost').setValue(total_cost_pc)
                            }
                }, c);
                }
                }
            },{
                cls: 'cTextAlign',
                xtype: 'numberfield',
                fieldLabel: 'BASIC COST',
                name: 'basic_cost',
                anchor: '85%',
                readOnly:true,
                allowBlank: false,
                id: 'ags-basic_cost'
            },{
                cls: 'cTextAlign',
                xtype: 'numberfield',
                fieldLabel: 'ADDITIONAL COST',
                name: 'additional_cost',
                anchor: '85%',
                readOnly:true,
                allowBlank: false,
                id: 'ags-additional_cost'
            },{
                cls: 'cTextAlign',
                xtype: 'numberfield',
                fieldLabel: 'OTHER CHARGES',
                name: 'otherCharges',
                anchor: '85%',
                allowBlank: false,
                id: 'ags-otherCharges',
                listeners: {
                'render': function(c) {
                c.getEl().on('keyup', function() {
                            var limit = Ext.getCmp('ags-maxRegular').getValue()
                            var qty = Ext.getCmp('ags-quantity').getValue()
                            Ext.getCmp('ags-basic_cost').show();
                            Ext.getCmp('ags-additional_cost').show();
                            Ext.getCmp('ags-remarks').show();
                            Ext.getCmp('ags-otherCharges').show();
                            Ext.getCmp('ags-total_cost').show();
                            
                            if(Ext.getCmp('ags-uom').getValue() == 'hph'){
                                Ext.getCmp('ags-quantity').show();
                                
                                var cbasic_cost_hph = 0.0;
                                var cadditional_cost_hph = 0.0;
                                var total_cost_hph = 0.0;
                                if(qty > limit){
                                    cbasic_cost_hph = Ext.getCmp('ags-regularPrice').getValue()
                                    var cdiff = qty - limit;
                                    cadditional_cost_hph = Ext.getCmp('ags-excessPrice').getValue() * cdiff
                                }else{
                                    cbasic_cost_hph = Ext.getCmp('ags-regularPrice').getValue() * qty
                                    cadditional_cost_hph = 0.0;
                                }
                                total_cost_hph = Number(Ext.getCmp('ags-basic_cost').getValue()) + Number(Ext.getCmp('ags-additional_cost').getValue()) + Number(Ext.getCmp('ags-otherCharges').getValue())
                                Ext.getCmp('ags-basic_cost').setValue(cbasic_cost_hph)
                                Ext.getCmp('ags-additional_cost').setValue(cadditional_cost_hph)
                                Ext.getCmp('ags-total_cost').setValue(total_cost_hph)
                            }
                            
                            
                            if(Ext.getCmp('ags-uom').getValue() == 'day'){
                                var ebasic_cost_day = Ext.getCmp('ags-regularPrice').getValue()
                                var cadditional_cost_day = Number(ebasic_cost_day) + Number(Ext.getCmp('ags-otherCharges').getValue())
                                Ext.getCmp('ags-basic_cost').setValue(ebasic_cost_day)
                                Ext.getCmp('ags-total_cost').setValue(cadditional_cost_day)
                                
                            }
                            if(Ext.getCmp('ags-uom').getValue() == 'pc'){
                                var dbasic_cost_pc = 0;
                                var dadditional_cost_pc = 0;
                                var total_cost_pc = 0.0;
                                if(qty > limit){
                                    dbasic_cost_pc = Ext.getCmp('ags-regularPrice').getValue()
                                    var ddiffs = qty - limit;
                                    dadditional_cost_pc = Ext.getCmp('ags-excessPrice').getValue() * ddiffs
                                    total_cost_pc = Number(dbasic_cost_pc) + Number(dadditional_cost_pc) + Number(Ext.getCmp('ags-otherCharges').getValue())
                                }else{
                                    dbasic_cost_pc = Ext.getCmp('ags-regularPrice').getValue() * qty
                                    dadditional_cost_pc = 0;
                                    total_cost_pc = Number(dbasic_cost_pc) + Number(dadditional_cost_pc) + Number(Ext.getCmp('ags-otherCharges').getValue())
                                }
                                
                                Ext.getCmp('ags-basic_cost').setValue(dbasic_cost_pc)
                                Ext.getCmp('ags-additional_cost').setValue(dadditional_cost_pc)
                                Ext.getCmp('ags-total_cost').setValue(total_cost_pc)
                            }
                }, c);
                }
                }
            },{
                cls: 'cTextAlign',
                xtype: 'textfield',
                fieldLabel: 'REMARKS',
                name: 'remarks',
                anchor: '85%',
                id: 'ags-remarks'
            },{
                cls: 'cTextAlign',
                xtype: 'numberfield',
                fieldLabel: 'TOTAL COST',
                name: 'total_cost',
                anchor: '85%',
                allowBlank: false,
                id: 'ags-total_cost',
                readOnly:true
            }], 
            buttons: [{
                id: 'mf.btn.add',
                text: 'Submit',
                handler: function() {
                    //fnAdminAmenitiesUpdateForm(argrid);
                    var form = Ext.getCmp('service-form').getForm();
                    if(form.isValid()){
                        form.submit({
                            url: 'admin/update/services.action',
                            waitMsg: 'loading...',
                            success: function(form, action) {
                                Ext.Msg.alert('Success', 'Successfully updated Service Request');
                                
                                var grid = Ext.getCmp('ars')
                                srstore.load();
                                fnResetForm(srgrid);
                            },
                            failure: function(form, action) {
                                Ext.Msg.alert('Warning', 'Error in updating Service Request');
                            }
                        })
                    }
                }
            },{
                text: 'Reset',
                disabled: false,
                handler: function() {
                    fnResetForm(srgrid);
                }
            }]
        }]
    
    });
    
   
    
   
    var treeDivs = '<div id="amenity-tree-div"></div>' +
    '<div id="service-tree-div"></div>' +
    '<div id="employee-tree-div"></div>' +
    '<div id="vehicle-tree-div"></div>';

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
        items: [{
            title: 'Alerts List',
            region:'west',
            margins: '5 0 0 0',
            cmargins: '5 5 0 0',
            width: 300,
            minSize: 100,
            maxSize: 250,
            html: treeDivs
        },{
            title: 'Main Content',
            collapsible: false,
            region:'center',
            margins: '5 0 0 0',
            xtype: 'tabpanel',
            activeTab: 0, // index or id
            items: [srgrid]
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
        renderTo: 'service-tree-div'
    });

    //Employee Tree
    var employeeTree = new Ext.tree.TreePanel({
        xtype : 'treepanel',
        autoScroll : true,
        loader : new Ext.tree.TreeLoader({
            url : 'alert/employee/view.action'
        }),
        root : {
            text : 'Employee Updates',
            id : 'data',
            cls: 'folder',
            expanded : true
        },
        listeners:{
             "click": function(node){
                 //alert(node)
             }
        },
        renderTo: 'employee-tree-div'
    });

    //Vehicle Tree
    var vehicleTree = new Ext.tree.TreePanel({
        xtype : 'treepanel',
        autoScroll : true,
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

function customRenderer(value, metaData, record, rowIndex, colIndex, store) {
    var opValue = value;
    
    if (value === "New") {
        metaData.css = 'redUnderlinedText';
    }else if (value === "On Process") {
        metaData.css = 'orangeUnderlinedText';
    }else if (value === "For Pick Up") {
        metaData.css = 'greenUnderlinedText';
    }else if (value === "Reserved") {
        metaData.css = 'blueUnderlinedText';
    }else if (value === "Booked") {
        metaData.css = 'greenUnderlinedText';
    }else if (value === "Cancelled") {
        metaData.css = 'redUnderlinedText';
    }else if (value === "Confirmed") {
        metaData.css = 'greenUnderlinedText';
    }else if (value === "Re-Scheduled") {
        metaData.css = 'orangeUnderlinedText';
    }else if (value === "Decline") {
        metaData.css = 'redUnderlinedText';
    }else if (value === "Done") {
        metaData.css = 'greenUnderlinedText';
    }else if (value === "ReIssue") {
        metaData.css = 'redUnderlinedText';
    }else if (value === "Renew") {
        metaData.css = 'redUnderlinedText';
    }else if (value === "Sold") {
        metaData.css = 'redUnderlinedText';
    }else if (value === "Change Request") {
        metaData.css = 'redUnderlinedText';
    }else if (value === "Update Profile") {
        metaData.css = 'redUnderlinedText';
    }else if (value === "End of Contract") {
        metaData.css = 'redUnderlinedText';
    }else if (value === "Cancel") {
        metaData.css = 'redUnderlinedText';
    }else if (value === "Re-Schedule") {
        metaData.css = 'redUnderlinedText';
    }
    return opValue;
}

function fnResetForm(theForm)
{
    theForm.getForm().reset();
    Ext.getCmp('mf.btn.add').setDisabled(false);
    Ext.getCmp('mf.btn.reset').setDisabled(false);
} 

function loadStore()
{
    Ext.getCmp('srgrid').getStore().load()
}
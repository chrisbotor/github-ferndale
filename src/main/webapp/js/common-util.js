/**
 * Contains common methods for all javascript files
 * Creation Date: 2015-03-04
 * Author: Racs
 * */


	// alert('in common-util.js!');
    
	/**
	 * 	This is used TO FORMAT MONEY (or any number with decimal points)
	 * */

    Number.prototype.format = function(n, x) {
        var re = '\\d(?=(\\d{' + (x || 3) + '})+' + (n > 0 ? '\\.' : '$') + ')';
        return this.toFixed(Math.max(0, ~~n)).replace(new RegExp(re, 'g'), '$&,');
    };
    
    
    /**
     * 	Removes the x icon in the alert message
     * */
    function removeXButton(alertObject)
    {
    	if (alertObject != null)
    	{
    		alertObject.getDialog().tools.close.hide();
    	}
    }
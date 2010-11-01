/**
 * Define JS initializer method for ajaxlaoder component.
 *
 */
Tapestry.Initializer.initAjaxLoader = function(params) {
	
	/** Observe zone-update */
	$(params.zone).observeAction(Tapestry.ZONE_UPDATED_EVENT, function() {
		$(params.loader).hide();
	});
	
	/** 
	 * Observe trigger process
	 */
	var trigger = $(params.trigger);
	if (trigger.tagName == "FORM") {
		$(trigger).observe(Tapestry.FORM_PROCESS_SUBMIT_EVENT,
				function() {
					$(params.loader).show();
				});
	} else {
		$(trigger).observe(Tapestry.TRIGGER_ZONE_UPDATE_EVENT,
				function() {
					$(params.loader).show();
		});
	}
};
/**
 * Removes all objects by the given argument. Using toString method to comparing obejcts.
 */
Array.prototype.removeObject = function(o) {
    for (var i in this) {
		if (this[i].toString() === o.toString()) {
			this.splice(i, 1);
		}
	}
}
JWLArticleForm = {
	_initAutocomplete: function() {
		var oThis = this;
			$('input[name=jwl-tags]')
				.bind("keydown", function(event) {
					if (event.keyCode === $.ui.keyCode.TAB && $(this).data("autocomplete").menu.active) {
						event.preventDefault();
					}
				})
				.autocomplete({
					minLength: 1,
					search: function(event, ui) {
						//prevent request for white-space
						if (oThis._extractLastToken(this.value).length == 0) {
							return false;
						}
					},
					source: function(request, response) {
						response($.ui.autocomplete.filter(
							oThis._tags, oThis._extractLastToken(request.term)));
					},
					focus: function() {
						return false;
					},
					select: function(event, ui) {
						var terms = oThis._split(this.value);
						terms.pop();
						terms.push(ui.item.value);
						terms.push("");
						this.value = terms.join(", ");
						return false;
					}
				});
		},
		_split: function (val) {
			var array = val.split( /,\s*/ );
			array.removeObject("");
			return array;
		},
		_extractLastToken: function(term) {
			return this._split(term).pop();
		},
		_tags : new Array(),
		_loadTags: function() {
			var oThis = this;
			$.getJSON(
				"ajax.seam?jwlmethod=ajax&jwlstate=tagsAutocomplete&jwlpresenter=Article", 
				{}, 
				function(data) {
					oThis._tags = data;
				}
						
			);
		},
		init: function() {
			this._loadTags();
			this._initAutocomplete();
		}
};
JWLArticleForm.init();
/*!
 * Copyright 2002 - 2014 Webdetails, a Pentaho company.  All rights reserved.
 *
 * This software was developed by Webdetails and is provided under the terms
 * of the Mozilla Public License, Version 2.0, or any later version. You may not use
 * this file except in compliance with the license. If you need a copy of the license,
 * please go to  http://mozilla.org/MPL/2.0/. The Initial Developer is Webdetails.
 *
 * Software distributed under the Mozilla Public License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or  implied. Please refer to
 * the license for the specific language governing your rights and limitations.
 */

/**
 * Configuration file for cdf core
 */

(function() {
  var requirePaths = requireCfg.paths,
      requireShims = requireCfg.shim;

  requireCfg.urlArgs = "ts=" + (new Date()).getTime();

  var isDebug = typeof document == "undefined" || document.location.href.indexOf("debug=true") > 0;

  if(typeof KARMA_RUN !== "undefined") { // test
    requirePaths['cdf'] = 'cdf/js-modules';
  } else if(typeof CONTEXT_PATH !== "undefined") { // production vs debug
    requirePaths['cdf'] = CONTEXT_PATH + 'api/repos/pentaho-cdf/js' + (isDebug ? '' : '/compressed');
  } else if(typeof FULLY_QUALIFIED_URL != "undefined") { // embedded production vs debug
    requirePaths['cdf'] = FULLY_QUALIFIED_URL + 'api/repos/pentaho-cdf/js' + (isDebug ? '' : '/compressed');
  } else { // build
    requirePaths['cdf'] = "cdf";
  }

  /*
   * component shim for Dashboard
   */
  requireShims['cdf/components/ButtonComponent'] = ['cdf/Dashboard'];
  requireShims['cdf/components/SelectComponent'] = ['cdf/Dashboard'];
  requireShims['cdf/components/TextComponent'] = ['cdf/Dashboard'];
  requireShims['cdf/components/TextInputComponent'] = ['cdf/Dashboard'];
  requireShims['cdf/components/TextareaInputComponent'] = ['cdf/Dashboard'];
  requireShims['cdf/components/DateInputComponent'] = ['cdf/Dashboard'];
  requireShims['cdf/components/DateRangeInputComponent'] = ['cdf/Dashboard'];
  requireShims['cdf/components/MonthPickerComponent'] = ['cdf/Dashboard'];
  requireShims['cdf/components/AutocompleteBoxComponent'] = ['cdf/Dashboard'];
  requireShims['cdf/components/CheckComponent'] = ['cdf/Dashboard'];
  requireShims['cdf/components/CommentsComponent'] = ['cdf/Dashboard'];
  requireShims['cdf/components/MultiButtonComponent'] = ['cdf/Dashboard'];
  requireShims['cdf/components/RadioComponent'] = ['cdf/Dashboard'];
  requireShims['cdf/components/SelectMultiComponent'] = ['cdf/Dashboard'];
  requireShims['cdf/components/MapComponent'] = ['cdf/Dashboard'];
  requireShims['cdf/components/MapBubbleComponent'] = ['cdf/Dashboard'];
  requireShims['cdf/components/SimpleAutoCompleteComponent'] = ['cdf/Dashboard'];
  
  /* other
  requireShims['cdf/components/ActionComponent'] = ['cdf/Dashboard'];
  requireShims['cdf/components/BaseComponent'] = ['cdf/Dashboard'];
  requireShims['cdf/components/FreeformComponent'] = ['cdf/Dashboard'];
  requireShims['cdf/components/InputBaseComponent'] = ['cdf/Dashboard'];
  requireShims['cdf/components/ManagedFreeformComponent'] = ['cdf/Dashboard'];
  requireShims['cdf/components/MdxQueryGroupComponent'] = ['cdf/Dashboard'];
  requireShims['cdf/components/QueryComponent'] = ['cdf/Dashboard'];
  requireShims['cdf/components/SelectBaseComponent'] = ['cdf/Dashboard'];
  requireShims['cdf/components/ToggleButtonComponent'] = ['cdf/Dashboard'];
  requireShims['cdf/components/UnmanagedComponent'] = ['cdf/Dashboard'];
  requireShims['cdf/components/NavigatorBaseComponent'] = ['cdf/Dashboard'];
  requireShims['cdf/components/MapBaseComponent'] = ['cdf/Dashboard'];
  */

})();

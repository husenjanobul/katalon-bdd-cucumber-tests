import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import groovy.json.JsonSlurper as JsonSlurper
import internal.GlobalVariable

def response = WebUI.callTestCase(findTestCase('Test Cases/Web API Tests/Advance Tests/DO NOT RUN/api-2-issue/Create issue/I call Create issue API to create a new ticket with specified information'), 
    [('authorization') : GlobalVariable.authorization, ('project_key') : project_key, ('issue_type') : issue_type, ('priority') : priority, ('summary') : summary, ('description') : description], 
    FailureHandling.STOP_ON_FAILURE)

WebUI.callTestCase(findTestCase('Test Cases/Web API Tests/Advance Tests/DO NOT RUN/api-2-issue/Create issue/The API is called successfully and new issue is created'), 
    [('response') : response], FailureHandling.STOP_ON_FAILURE)

def jsonSlurper = new JsonSlurper()

def jsonResponse = jsonSlurper.parseText(response.getResponseText())

def issueResponse = WebUI.callTestCase(findTestCase('Test Cases/Web API Tests/Advance Tests/DO NOT RUN/api-2-search/Search issues/I use Search issue API to get the created issue'), 
    [('authorization') : GlobalVariable.authorization, ('issue_key') : jsonResponse.key], FailureHandling.STOP_ON_FAILURE)

WebUI.callTestCase(findTestCase('Test Cases/Web API Tests/Advance Tests/DO NOT RUN/api-2-search/Search issues/The returned issue has the same value as specified information'), 
    [('authorization') : GlobalVariable.authorization, ('response') : issueResponse, ('project_key') : project_key, ('issue_type') : issue_type, ('priority') : priority, ('summary') : summary
        , ('description') : description], FailureHandling.STOP_ON_FAILURE)


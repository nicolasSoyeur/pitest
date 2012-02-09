/*
 * Copyright 2011 Henry Coles
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package org.pitest.mutationtest.report;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.pitest.mutationtest.HtmlReportFactory;

public class OutputFormatTest {

  @Mock
  ResultOutputStrategy output;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void htmlFormatShouldReturnAnHtmlReportFactory() {
    assertTrue(OutputFormat.HTML.createFactory(this.output) instanceof HtmlReportFactory);
  }

  @Test
  public void csvFormatShouldReturnACSVReportFactory() {
    assertTrue(OutputFormat.CSV.createFactory(this.output) instanceof CSVReportFactory);
  }

  @Test
  public void xmlFormatShouldReturnAnXMLReportFactory() {
    assertTrue(OutputFormat.XML.createFactory(this.output) instanceof XMLReportFactory);
  }

}
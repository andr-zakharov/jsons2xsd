package com.ethlo.jsons2xsd.cli;

/*-
 * #%L
 * jsons2xsd
 * %%
 * Copyright (C) 2014 - 2025 Morten Haraldsen (ethlo)
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * #L%
 */

import com.beust.jcommander.Parameter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ConfigJCommanderParameters
{
    @Parameter(names = {"--targetNamespace"}, description = "Target namespace for the generated XSD. The root schema element will include attributeFormDefault=\"qualified\".")
    String targetNamespace;

    @Parameter(names = {"--pathToDefinitions"}, description = "Specify the path to the root definitions in the JSON schema.")
    String pathToDefinitions;
    
    @Parameter(names = {"--nsPrefix"}, description = "The nsPrefix is used to match and remove a specific prefix from JSON references. This helps in cleaning up the references and making them more concise in the resulting XSD.")
    String nsPrefix;

    @Parameter(names = {"--nsAlias"}, description = "The alias is added as an XML namespace declaration in the root element of the XSD: " +
            "<schema xmlns:nsAlias=\"http://example.com/namespace\" ...> " +
            "It is then used as a prefix for type references in the XSD: " +
            "<element name=\"example\" type=\"nsAlias:ExampleType\"/>.")
    String nsAlias;

    @Parameter(names={"--createRootElement"}, description = "Create a root element in the XSD.")
    Boolean createRootElement;

    @Parameter(names={"--rootElement"}, description = "Name of the root element.")
    String rootElement;

    @Parameter(names={"--attributesQualified"}, description = "Specifies whether attributes in the XSD should be qualified. If enabled, the root schema element will include attributeFormDefault=\"qualified\".")
    Boolean attributesQualified;

    @Parameter(names={"--validateXsdSchema"}, description = "Validate the generated XSD schema against the XML Schema Definition standard.")
    Boolean validateXsdSchema;
 
    @Parameter(names={"--unwrapArrays"}, description = "Enable unwrap arrays. " +
            "The array elements are represented directly in the XSD without being wrapped in a complex type or sequence. This approach simplifies the structure by \"unwrapping\" the array, making individual items directly accessible. " +
            "When not specified, arrays are represented as a complex type with a sequence in the XSD, adding an extra layer of structure where the array is explicitly defined as a container for its items.")
    Boolean unwrapArrays;

    @Parameter(names={"--elementsQualified"}, description = "Specifies whether elements in the XSD should be qualified. If enabled, the root schema element will include attributeFormDefault=\"qualified\".")
    Boolean elementsQualified = false;

    @Parameter(names={"--name"}, description = "Name of the root complexType.")
    String name;

    Boolean includeOnlyUsedTypes;
    Function<String,String> itemNameMapper;
    Map<String, String> typeMapping;
    Boolean ignoreUnknownFormats;

    @Parameter(description = "<input-json-schema> <output-xsd-file>")
    List<String> files = new ArrayList<>();
}

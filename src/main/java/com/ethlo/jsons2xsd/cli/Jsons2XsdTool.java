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

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import com.ethlo.jsons2xsd.Config;
import com.ethlo.jsons2xsd.Config.Builder;
import com.ethlo.jsons2xsd.Jsons2Xsd;
import com.ethlo.jsons2xsd.XmlUtil;
import java.util.Optional;
import org.w3c.dom.Document;

import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Jsons2XsdTool
{

    public static void main(String[] args) throws Exception {
        new Jsons2XsdTool().run(args);
    }

    public void run(String... args) throws Exception {
        ConfigJCommanderParameters params = new ConfigJCommanderParameters();
        JCommander jCommander = new JCommander(params);
        jCommander.setProgramName("jsons2xsd");

        try {
            jCommander.parse(args);

            if (params.files == null || params.files.size() != 2) {
                throw new ParameterException("Exactly 2 arguments required: <input-json-schema> <output-xsd-file>");
            }
            String inputJsonSchema = params.files.get(0);
            String outputXsdFile = params.files.get(1);

            Builder builder = new Builder();
            Optional.ofNullable(params.targetNamespace).ifPresent(builder::targetNamespace);
            Optional.ofNullable(params.pathToDefinitions).ifPresent(builder::pathToDefinitions);
            Optional.ofNullable(params.nsPrefix).ifPresent(builder::nsPrefix);
            Optional.ofNullable(params.nsAlias).ifPresent(builder::nsAlias);
            Optional.ofNullable(params.createRootElement).ifPresent(builder::createRootElement);
            Optional.ofNullable(params.name).ifPresent(builder::name);
            Optional.ofNullable(params.attributesQualified).ifPresent(builder::attributesQualified);
            Optional.ofNullable(params.includeOnlyUsedTypes).ifPresent(builder::includeOnlyUsedTypes);
            Optional.ofNullable(params.validateXsdSchema).ifPresent(builder::validateXsdSchema);
            Optional.ofNullable(params.ignoreUnknownFormats).ifPresent(builder::ignoreUnknownFormats);
            Optional.ofNullable(params.rootElement).ifPresent(builder::rootElement);
            Optional.ofNullable(params.unwrapArrays).ifPresent(builder::unwrapArrays);
            Optional.ofNullable(params.elementsQualified).ifPresent(builder::elementsQualified);
            Config config = builder.build();

            System.out.println("Input JSON Schema: " + inputJsonSchema);
            Document xsdDocument = Jsons2Xsd.convert(new FileReader(inputJsonSchema), config);
            final String xsd = XmlUtil.asXmlString(xsdDocument.getDocumentElement());
            Files.write(Paths.get(outputXsdFile), xsd.getBytes());
            System.out.println("Output XSD File: " + outputXsdFile);

        } catch (ParameterException e) {
            System.err.println("Error: " + e.getMessage());
            jCommander.usage();
            System.exit(1);
        }
    }
}

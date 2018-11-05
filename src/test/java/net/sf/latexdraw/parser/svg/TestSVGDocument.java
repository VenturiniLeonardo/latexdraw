package net.sf.latexdraw.parser.svg;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestSVGDocument {
	protected SVGDocument doc1;
	protected SVGDocument doc2;

	@BeforeEach
	public void setUp() throws MalformedSVGDocument, URISyntaxException, IOException {
		doc1 = new SVGDocument();
		doc2 = new SVGDocument(new URI("src/test/resources/test.svg"));
	}

	@Test
	public void testSVGDocumentNULL() {
		assertThrows(IllegalArgumentException.class, () -> new SVGDocument(null));
	}

	@Test
	public void testSVGDocumentInvalidStr() {
		assertThrows(FileNotFoundException.class, () -> new SVGDocument(new URI("dfqsfg")));
	}

	@Test
	public void testSVGDocument() throws MalformedSVGDocument, URISyntaxException, IOException {
		final SVGDocument doc = new SVGDocument(new URI("src/test/resources/test.svg"));
		assertNotNull(doc.getFirstChild());
		assertNotNull(doc.getLastChild());
	}

	@Test
	public void testSVGDocument2() {
		final SVGDocument doc = new SVGDocument();
		assertNotNull(doc.getFirstChild());
		assertNotNull(doc.getLastChild());
		assertNull(doc.getDocumentURI());
	}

	@Test
	public void testAdoptNodeNULL() {
		assertThrows(DOMException.class, () -> doc1.adoptNode(null));
	}

	@Test
	public void testAdoptNodeKO() {
		assertThrows(DOMException.class, () -> doc1.adoptNode(new SVGComment("", doc2)));
	}

	@Test
	public void testAdoptNodeOK() {
		final SVGSVGElement elt = new SVGSVGElement(doc2);
		doc1.adoptNode(elt);
		assertEquals(doc1, elt.getOwnerDocument());
		assertEquals(elt, doc1.getFirstChild());
	}

	@Test
	public void testGetDocumentElement() {
		assertNotNull(doc1.getDocumentElement());
	}

	@Test
	public void testSetDocumentURI() {
		doc1.setDocumentURI(null);
		assertNull(doc1.getDocumentURI());
		doc1.setDocumentURI("coucou");
		assertEquals("coucou", doc1.getDocumentURI());
	}

	@Test
	public void testSetXmlStandalone() {
		doc1.setXmlStandalone(false);
		assertFalse(doc1.getXmlStandalone());
		doc1.setXmlStandalone(true);
		assertTrue(doc1.getXmlStandalone());
	}

	@Test
	public void testSetXmlVersion() {
		doc1.setXmlVersion(null);
		assertNull(doc1.getXmlVersion());
		doc1.setXmlVersion("coucou");
		assertEquals("coucou", doc1.getXmlVersion());
	}

	@Test
	public void testUselessMethods() {
		assertNull(doc1.getAttributes());
		assertNotNull(doc1.getChildNodes());
		assertEquals(0, doc1.getChildNodes().getLength());
		assertNull(doc1.getNextSibling());
		assertNull(doc1.getPreviousSibling());
		assertNull(doc2.getAttributes());
		assertNotNull(doc2.getChildNodes());
		assertEquals(0, doc2.getChildNodes().getLength());
		assertNull(doc2.getNextSibling());
		assertNull(doc2.getPreviousSibling());
		assertNull(doc2.getNodeValue());
		assertNull(doc2.getOwnerDocument());
		assertNull(doc2.getParentNode());
		assertNull(doc1.getNodeValue());
		assertNull(doc1.getOwnerDocument());
		assertNull(doc1.getParentNode());
		assertFalse(doc2.hasAttributes());
		assertFalse(doc1.hasAttributes());
		assertNull(doc1.getDoctype());
	}

	@Test
	public void testGetFirstChild() {
		assertNotNull(doc1.getFirstChild());
		assertNotNull(doc2.getFirstChild());
	}

	@Test
	public void testGetLastChild() {
		assertNotNull(doc1.getLastChild());
		assertNotNull(doc2.getLastChild());
		assertEquals(doc1.getLastChild(), doc1.getFirstChild());
		assertEquals(doc2.getLastChild(), doc2.getFirstChild());
	}

	@Test
	public void testGetNodeName() {
		assertEquals("#document", doc1.getNodeName());
		assertEquals("#document", doc2.getNodeName());
	}

	@Test
	public void testGetNodeType() {
		assertEquals(Node.DOCUMENT_NODE, doc1.getNodeType());
		assertEquals(Node.DOCUMENT_NODE, doc2.getNodeType());
	}

	@Test
	public void testHasChildNode() {
		assertTrue(doc1.hasChildNodes());
		assertTrue(doc2.hasChildNodes());
	}

	@Test
	public void testIsEqualNode() {
		final SVGDocument doc = new SVGDocument();
		assertTrue(doc1.isEqualNode(doc));
		assertFalse(doc1.isEqualNode(null));
		assertFalse(doc1.isEqualNode(doc2));
	}

	@Test
	public void testIsEqualNodeURI() throws MalformedSVGDocument, URISyntaxException, IOException {
		final SVGDocument doc = new SVGDocument(new URI("src/test/resources/test.svg"));
		assertTrue(doc2.isEqualNode(doc));
		assertFalse(doc2.isEqualNode(null));
		assertFalse(doc2.isEqualNode(doc1));
	}

	@Test
	public void testIsSameNode() {
		assertTrue(doc1.isEqualNode(doc1));
		assertTrue(doc2.isEqualNode(doc2));
		assertFalse(doc1.isEqualNode(null));
		assertFalse(doc2.isEqualNode(null));
		assertFalse(doc1.isEqualNode(doc2));
		assertFalse(doc2.isEqualNode(doc1));
	}

	@Test
	public void testCreateElementKO() {
		assertThrows(DOMException.class, () -> doc1.createElement(null));
	}

	@Test
	public void testCreateElementOK() {
		final SVGElement elt = (SVGElement) doc1.createElement("test");
		assertEquals("test", elt.getNodeName());
		assertEquals(doc1, elt.getOwnerDocument());
	}

	@Test
	public void testCreateTextNodeKO() {
		assertThrows(DOMException.class, () -> doc1.createTextNode(null));
	}


	@Test
	public void testCreateTextNodeOK() {
		final SVGText elt = (SVGText) doc1.createTextNode("test");
		assertEquals("test", elt.getData());
		assertEquals(doc1, elt.getOwnerDocument());
	}

	@Test
	public void testCreateCommentKO() {
		assertThrows(DOMException.class, () -> doc1.createComment(null));
	}

	@Test
	public void testCreateCommentOK() {
		final SVGComment elt = (SVGComment) doc1.createComment("test");
		assertEquals("test", elt.getData());
		assertEquals(doc1, elt.getOwnerDocument());
	}
}

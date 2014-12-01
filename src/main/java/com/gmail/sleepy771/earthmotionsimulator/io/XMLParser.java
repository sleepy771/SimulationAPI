package com.gmail.sleepy771.earthmotionsimulator.io;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import Jama.Matrix;

import com.gmail.sleepy771.earthmotionsimulator.BodyID;
import com.gmail.sleepy771.earthmotionsimulator.SolarSystem;
import com.gmail.sleepy771.earthmotionsimulator.datastuct.Storage;
import com.gmail.sleepy771.earthmotionsimulator.objects.Body;
import com.gmail.sleepy771.earthmotionsimulator.objects.Planet;
import com.gmail.sleepy771.earthmotionsimulator.simulation.SimulationSystem;

public class XMLParser {
	private final File file;
	private Storage storage;

	public XMLParser(File f) {
		this.file = f;
	}
	
	public void setStorage(Storage<?> s) {
		this.storage = s;
	}
	
	public Storage<?> getStorage() {
		return this.storage;
	}

	public SimulationSystem<Body> parse() throws ParserConfigurationException,
			SAXException, IOException, DOMException, ParseException {
		List<Body> bodies = new ArrayList<Body>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(file);

		SimulationSystem<Body> system;

		SimpleDateFormat format = new SimpleDateFormat(
				"yyyy-MMM-dd HH:mm:ss.SSSS");
		
		double startTime = format.parse(
				document.getDocumentElement().getAttributes().getNamedItem("date").getNodeValue())
				.getTime();
		double dt = Double.valueOf(document.getDocumentElement().getAttributes().getNamedItem("step")
				.getNodeValue()) * 24 * 3600 * 1000;
		NodeList nodes = document.getDocumentElement().getChildNodes();
		for (int k = 1; k < nodes.getLength(); k++) {
			Node bodyNode = nodes.item(k);
			if (!bodyNode.getNodeName().equalsIgnoreCase("Body"))
				continue;
			double mass = -1.;
			String name = null;
			int id = -1;
			mass = Double.valueOf(bodyNode.getAttributes().getNamedItem("mass")
					.getNodeValue());
			name = bodyNode.getAttributes().getNamedItem("name").getNodeValue();
			id = Integer.valueOf(bodyNode.getAttributes().getNamedItem("id")
					.getNodeValue());
			double[] position = new double[3];
			double[] momentum = new double[3];
			position[0] = Double.valueOf(bodyNode.getAttributes()
					.getNamedItem("x").getNodeValue());
			position[1] = Double.valueOf(bodyNode.getAttributes()
					.getNamedItem("y").getNodeValue());
			position[2] = Double.valueOf(bodyNode.getAttributes()
					.getNamedItem("z").getNodeValue());
			momentum[0] = Double.valueOf(bodyNode.getAttributes()
					.getNamedItem("vx").getNodeValue());
			momentum[1] = Double.valueOf(bodyNode.getAttributes()
					.getNamedItem("vy").getNodeValue());
			momentum[2] = Double.valueOf(bodyNode.getAttributes()
					.getNamedItem("vz").getNodeValue());
			//Matrix r = new Matrix(position, 3).times(UnitConverter.AU2M);
			//Matrix p = new Matrix(momentum, 3).times(mass * UnitConverter.AUPDAY2MPS);
			//bodies.add(new Planet(new PlanetIdentificator(name, id), mass, p, r));
		}

		system = new SolarSystem(startTime, dt, bodies, storage);
		return system;
	}
}

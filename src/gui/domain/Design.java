package gui.domain;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import uml.graphics.Connection;
import uml.graphics.Thing;
import figure.graphics.Figure;
import figure.model.Logic;

class Design  implements Observer {

	ArrayList<Connection> connectors = new ArrayList<Connection>();
	ArrayList<Thing> figures = new ArrayList<Thing>();
	ArrayList<Thing> historyFigures = new ArrayList<Thing>();

	private Thing connectReady;
	
	private void noConnectables() {
		for (Thing f : figures)
			f.setReady(false);
	}

	boolean unConnected(Thing source, Thing figure) {
		for (Connection con : connectors) {
			if (con.isConnected(source, figure))
				return false;
		}
		return true;
	}

	void prepareConnection(Thing umlthing) {
		connectReady = umlthing;
		for (Thing f : figures) {
			if (f != connectReady && unConnected(connectReady, f))
				f.setReady(true);
		}
	}

	Figure finishConnection(Thing target) {
		Connection c = new Connection(connectReady, target);
		connectors.add(c);
		noConnectables();
		return c;
	}
	
	void setShadow(float d) {
		for (Figure figure : all()) {
			figure.setShadow(d);
		}
	}
	
	ArrayList<Figure> all() {
		return Logic.concat(figures, connectors);
	}
	
	void addFigure(Thing figure) {
		figures.add(figure);
	}

	void removeFigure(Figure figure) {
		figures.remove(figure);
	}

	void removeConnector(Figure figure) {
		connectors.remove(figure);
	}



	@Override
	public void update(Observable o, Object arg) {
		figures = historyFigures;
		System.out.println("update Observable");
	}

}

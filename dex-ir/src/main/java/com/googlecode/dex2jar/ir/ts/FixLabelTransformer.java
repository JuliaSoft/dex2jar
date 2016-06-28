package com.googlecode.dex2jar.ir.ts;

import java.util.List;

import com.googlecode.dex2jar.ir.IrMethod;
import com.googlecode.dex2jar.ir.Trap;
import com.googlecode.dex2jar.ir.stmt.BaseSwitchStmt;
import com.googlecode.dex2jar.ir.stmt.JumpStmt;
import com.googlecode.dex2jar.ir.stmt.LabelStmt;
import com.googlecode.dex2jar.ir.stmt.Stmt;
import com.googlecode.dex2jar.ir.stmt.StmtList;

public class FixLabelTransformer extends StatedTransformer {

	@Override
	public void transform(IrMethod method) {
		transformReportChanged(method);
	}

	@Override
	public boolean transformReportChanged(IrMethod method) {

		boolean changed = false;

		// FLAVIOSTUFF
		// remove collapsed labels
		for (Stmt s : method.stmts) {
			if (s instanceof LabelStmt && s.getNext() != null && s.getNext() instanceof LabelStmt) {

				LabelStmt l1 = (LabelStmt) s;
				LabelStmt l2 = (LabelStmt) s.getNext();

				if (!thereIsSomeJumpTo(method, l1) && l1.lineNumber != -1 && l2.lineNumber != -1) {
					method.stmts.remove(s);
					method.locals.remove(s.getOp1());
					changed = true;
				}
			}
		}

		return changed;
	}

	private boolean thereIsSomeJumpTo(IrMethod method, LabelStmt goal) {

		StmtList stmts = method.stmts;
		for (Stmt s : stmts) {
			if (s instanceof JumpStmt) {
				JumpStmt jmp = (JumpStmt) s;
				if (jmp.getTarget() == goal)
					return true;
			}
			if (s instanceof BaseSwitchStmt) {
				BaseSwitchStmt sw = (BaseSwitchStmt) s;
				for (LabelStmt t : sw.targets)
					if (t == goal)
						return true;
			}
		}

		List<Trap> traps = method.traps;
		for (Trap t : traps) {
			if (t.start == goal)
				return true;
			if (t.end == goal)
				return true;
			for (LabelStmt l : t.handlers)
				if (l == goal)
					return true;
		}

		return false;
	}

}
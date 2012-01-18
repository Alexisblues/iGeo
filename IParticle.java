/*---

    iGeo - http://igeo.jp

    Copyright (c) 2002-2012 Satoru Sugihara

    This file is part of iGeo.

    iGeo is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as
    published by the Free Software Foundation, version 3.

    iGeo is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with iGeo.  If not, see <http://www.gnu.org/licenses/>.

---*/

package igeo;

import java.util.ArrayList;

import igeo.gui.*;
/**
   Class of an implementation of IDynamics to have physical attributes of point.
   It has attributes of position, velocity, acceleration, force, and mass.
   Position is provided from outside to be linked.
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public class IParticle extends IDynamicsBase implements IParticleI, IVecI{
    
    static double defaultFriction = 0.0;
    
    //public IObject parent=null;
    
    public double mass=1.0;
    public IVec pos;
    public IVec vel;
    //public IVec acc;
    public IVec frc;
    boolean fixed=false;
    public double friction = defaultFriction;
    
    public IParticle(IVec pos){ this.pos = pos; initParticle(); }
    public IParticle(IVec pos, IObject parent){ super(parent); this.pos = pos; initParticle(); }
    
    public IParticle(IVecI p){ pos = p.get(); initParticle(); }
    public IParticle(IVecI p, IObject parent){ super(parent); pos = p.get(); initParticle(); }
    
    public IParticle(double x, double y, double z){ pos = new IVec(x,y,z); initParticle(); }
    public IParticle(double x, double y, double z, IObject parent){ super(parent); pos = new IVec(x,y,z); initParticle(); }
    
    public IParticle(IPoint pt){ super(pt); pos = pt.pos; initParticle(); }
    public IParticle(IPointR pt){ super(pt); pos = pt.pos.get(); initParticle(); }
    
    public IParticle(IParticle ptcl){
	super(ptcl.parent);
	pos = ptcl.pos.dup();
	initParticle();
    }
    
    public IParticle(IParticle ptcl, IObject parent){
	super(parent);
	pos = ptcl.pos.dup();
	initParticle();
    }
    
    
    
    public IParticle(IVec pos, IVec vel){ this.pos = pos; initParticle(vel); }
    public IParticle(IVec pos, IVec vel, IObject parent){ super(parent); this.pos = pos; initParticle(vel); }
    
    public IParticle(IVecI p, IVecI v){ pos = p.get(); initParticle(v); }
    public IParticle(IVecI p, IVecI v, IObject parent){ super(parent); pos = p.get(); initParticle(v); }
    
    public IParticle(double x, double y, double z, double vx, double vy, double vz){
	pos = new IVec(x,y,z); initParticle(new IVec(vx,vy,vz));
    }
    public IParticle(double x, double y, double z, double vx, double vy, double vz,
		     IObject parent){
	super(parent); pos = new IVec(x,y,z); initParticle(new IVec(vx,vy,vz));
    }
    
    public IParticle(IPoint pt, IVecI v){ super(pt); pos = pt.pos; initParticle(v); }
    public IParticle(IPointR pt, IVecI v){ super(pt); pos = pt.pos.get(); initParticle(v); }
    
    public IParticle(IParticle ptcl, IVecI v){
	super(ptcl.parent);
	pos = ptcl.pos.dup();
	initParticle(v);
    }
    
    public IParticle(IParticle ptcl, IVecI v, IObject parent){
	super(parent);
	pos = ptcl.pos.dup();
	initParticle(v);
    }
    
    
    synchronized public void initParticle(){
	vel = new IVec();
	//acc = new IVec();
	frc = new IVec();
    }
    synchronized public void initParticle(IVec v){
	vel = v;
	frc = new IVec();
    }
    synchronized public void initParticle(IVecI v){
	vel = v.get();
	frc = new IVec();
    }
    
    public IParticle dup(){ return new IParticle(this); }
    // temporary
    //public IParticle dup(){ return this; }
    
    
    // implementation of IDynamics
    //public IObject parent(){ return parent; }
    //public ISubobject parent(IObject parent){ this.parent=parent; return this; }
    
    synchronized public IParticle fix(){ fixed=true; return this; }
    synchronized public IParticle unfix(){ fixed=false; return this; }
    
    synchronized public double mass(){ return mass; }
    synchronized public IParticle mass(double mass){ this.mass=mass; return this; }
    
    synchronized public IVec position(){ return pos(); }
    synchronized public IParticle position(IVecI v){ return pos(v); }
    
    synchronized public IVec pos(){ return pos; }
    synchronized public IParticle pos(IVecI v){ pos.set(v); return this; }
    
    synchronized public IVec velocity(){ return vel(); }
    synchronized public IParticle velocity(IVecI v){ return vel(v); }
    
    synchronized public IVec vel(){ return vel; }
    synchronized public IParticle vel(IVecI v){ vel.set(v); return this; }
    
    /** get acceleration; acceleration is calculated from frc and mass */
    synchronized public IVec acceleration(){ return acc(); }
    /** get acceleration; acceleration is calculated from frc and mass */
    synchronized public IVec acc(){ return frc.dup().div(mass); } 
    
    //synchronized public IVec acceleration(){ return acc(); }
    //synchronized public IParticle acceleration(IVec v){ acc(v); return this; }
    //synchronized public IVec acc(){ return acc; }
    //synchronized public IParticle acc(IVec v){ acc.set(v); return this; }
    
    synchronized public IVec force(){ return frc(); }
    synchronized public IParticle force(IVecI v){ return frc(v); }
    
    synchronized public IVec frc(){ return frc; }
    synchronized public IParticle frc(IVecI v){ frc.set(v); return this; }
    
    synchronized public double friction(){ return fric(); }
    synchronized public IParticle friction(double friction){ return fric(friction); }
    
    synchronized public double fric(){ return friction; }
    synchronized public IParticle fric(double friction){ this.friction=friction; return this; }
    /* alias of friction */
    public double decay(){ return fric(); }
    /* alias of friction */
    public IParticle decay(double d){ return fric(d); }
    
    /** adding force */
    synchronized public IParticle push(IVecI f){ frc.add(f); return this; }
    /** adding negative force */
    synchronized public IParticle pull(IVecI f){ frc.sub(f); return this; }
    /** adding force (alias of push) */
    synchronized public IParticle addForce(IVecI f){ return push(f); }

    /** setting force zero */
    synchronized public IParticle reset(){ frc.zero(); return this; }
    /** setting force zero (alias of reset()) */
    synchronized public IParticle resetForce(){ return reset(); }
    
    synchronized public void interact(ArrayList<IDynamics> dynamics){}
    
    synchronized public void update(){
	if(fixed) return;
	vel.add(frc.mul(IConfig.updateRate/mass)).mul(1.0-friction);
	pos.add(vel.dup().mul(IConfig.updateRate));
	frc.zero();
	//if(parent!=null) parent.updateGraphic(); // graphic update
	//super.update();
	updateTarget();
    }
    
    
    /************************************************************************
     * implements of IVecI
     ***********************************************************************/
    
    public double x(){ return pos.x(); }
    public double y(){ return pos.y(); }
    public double z(){ return pos.z(); }
    public IVec get(){ return pos.get(); }
    
    public IVec2 to2d(){ return pos.to2d(); }
    public IVec4 to4d(){ return pos.to4d(); }
    public IVec4 to4d(double w){ return pos.to4d(w); }
    public IVec4 to4d(IDoubleI w){ return pos.to4d(w); }
    
    public IDouble getX(){ return pos.getX(); }
    public IDouble getY(){ return pos.getY(); }
    public IDouble getZ(){ return pos.getZ(); }
    
    public IParticle set(IVecI v){ pos.set(v); return this; }
    public IParticle set(double x, double y, double z){ pos.set(x,y,z); return this;}
    public IParticle set(IDoubleI x, IDoubleI y, IDoubleI z){ pos.set(x,y,z); return this; }
    
    public IParticle add(double x, double y, double z){ pos.add(x,y,z); return this; }
    public IParticle add(IDoubleI x, IDoubleI y, IDoubleI z){ pos.add(x,y,z); return this; }    
    public IParticle add(IVecI v){ pos.add(v); return this; }
    
    public IParticle sub(double x, double y, double z){ pos.sub(x,y,z); return this; }
    public IParticle sub(IDoubleI x, IDoubleI y, IDoubleI z){ pos.sub(x,y,z); return this; }
    public IParticle sub(IVecI v){ pos.sub(v); return this; }
    public IParticle mul(IDoubleI v){ pos.mul(v); return this; }
    public IParticle mul(double v){ pos.mul(v); return this; }
    public IParticle div(IDoubleI v){ pos.div(v); return this; }
    public IParticle div(double v){ pos.div(v); return this; }
    public IParticle neg(){ pos.neg(); return this; }
    public IParticle rev(){ return neg(); }
    public IParticle flip(){ return neg(); }

    public IParticle zero(){ pos.zero(); return this; }
    
    public IParticle add(IVecI v, double f){ pos.add(v,f); return this; }
    public IParticle add(IVecI v, IDoubleI f){ pos.add(v,f); return this; }
    
    public IParticle add(double f, IVecI v){ return add(v,f); }
    public IParticle add(IDoubleI f, IVecI v){ return add(v,f); }
    
    
    public double dot(IVecI v){ return pos.dot(v); }
    public double dot(double vx, double vy, double vz){ return pos.dot(vx,vy,vz); }
    public double dot(ISwitchE e, IVecI v){ return pos.dot(e,v); }
    public IDouble dot(ISwitchR r, IVecI v){ return pos.dot(r,v); }
    
    // creating IParticle is too much (in terms of memory occupancy)
    public IVec cross(IVecI v){ return pos.cross(v); }
    public IVec cross(double vx, double vy, double vz){ return pos.cross(vx,vy,vz); }
    //public IParticle cross(IVecI v){ return dup().set(pos.cross(v)); }
    
    public double len(){ return pos.len(); }
    public double len(ISwitchE e){ return pos.len(e); }
    public IDouble len(ISwitchR r){ return pos.len(r); }
    
    public double len2(){ return pos.len2(); }
    public double len2(ISwitchE e){ return pos.len2(e); }
    public IDouble len2(ISwitchR r){ return pos.len2(r); }
    
    public IParticle len(IDoubleI l){ pos.len(l); return this; }
    public IParticle len(double l){ pos.len(l); return this; }
    
    public IParticle unit(){ pos.unit(); return this; }
    
    public double dist(IVecI v){ return pos.dist(v); }
    public double dist(double vx, double vy, double vz){ return pos.dist(vx,vy,vz); }
    public double dist(ISwitchE e, IVecI v){ return pos.dist(e,v); }
    public IDouble dist(ISwitchR r, IVecI v){ return pos.dist(r,v); }
    
    public double dist2(IVecI v){ return pos.dist2(v); }
    public double dist2(double vx, double vy, double vz){ return pos.dist2(vx,vy,vz); }
    public double dist2(ISwitchE e, IVecI v){ return pos.dist2(e,v); }
    public IDouble dist2(ISwitchR r, IVecI v){ return pos.dist2(r,v); }
    
    public boolean eq(IVecI v){ return pos.eq(v); }
    public boolean eq(double vx, double vy, double vz){ return pos.eq(vx,vy,vz); }
    public boolean eq(ISwitchE e, IVecI v){ return pos.eq(e,v); }
    public IBool eq(ISwitchR r, IVecI v){ return pos.eq(r,v); }
    
    public boolean eq(IVecI v, double tolerance){ return pos.eq(v,tolerance); }
    public boolean eq(double vx, double vy, double vz, double tolerance){ return pos.eq(vx,vy,vz,tolerance); }
    public boolean eq(ISwitchE e, IVecI v, double tolerance){ return pos.eq(e,v,tolerance); }
    public IBool eq(ISwitchR r, IVecI v, IDoubleI tolerance){ return pos.eq(r,v,tolerance); }
    
    public boolean eqX(IVecI v){ return pos.eqX(v); }
    public boolean eqY(IVecI v){ return pos.eqY(v); }
    public boolean eqZ(IVecI v){ return pos.eqZ(v); }
    public boolean eqX(double vx){ return pos.eqX(vx); }
    public boolean eqY(double vy){ return pos.eqY(vy); }
    public boolean eqZ(double vz){ return pos.eqZ(vz); }
    public boolean eqX(ISwitchE e, IVecI v){ return pos.eqX(e,v); }
    public boolean eqY(ISwitchE e, IVecI v){ return pos.eqY(e,v); }
    public boolean eqZ(ISwitchE e, IVecI v){ return pos.eqZ(e,v); }
    public IBool eqX(ISwitchR r, IVecI v){ return pos.eqX(r,v); }
    public IBool eqY(ISwitchR r, IVecI v){ return pos.eqY(r,v); }
    public IBool eqZ(ISwitchR r, IVecI v){ return pos.eqZ(r,v); }
    
    public boolean eqX(IVecI v, double tolerance){ return pos.eqX(v,tolerance); }
    public boolean eqY(IVecI v, double tolerance){ return pos.eqY(v,tolerance); }
    public boolean eqZ(IVecI v, double tolerance){ return pos.eqZ(v,tolerance); }
    public boolean eqX(double vx, double tolerance){ return pos.eqX(vx,tolerance); }
    public boolean eqY(double vy, double tolerance){ return pos.eqY(vy,tolerance); }
    public boolean eqZ(double vz, double tolerance){ return pos.eqZ(vz,tolerance); }
    public boolean eqX(ISwitchE e, IVecI v, double tolerance){ return pos.eqX(e,v,tolerance); }
    public boolean eqY(ISwitchE e, IVecI v, double tolerance){ return pos.eqY(e,v,tolerance); }
    public boolean eqZ(ISwitchE e, IVecI v, double tolerance){ return pos.eqZ(e,v,tolerance); }
    public IBool eqX(ISwitchR r, IVecI v, IDoubleI tolerance){ return pos.eqX(r,v,tolerance); }
    public IBool eqY(ISwitchR r, IVecI v, IDoubleI tolerance){ return pos.eqY(r,v,tolerance); }
    public IBool eqZ(ISwitchR r, IVecI v, IDoubleI tolerance){ return pos.eqZ(r,v,tolerance); }
    
    
    public double angle(IVecI v){ return pos.angle(v); }
    public double angle(double vx, double vy, double vz){ return pos.angle(vx,vy,vz); }
    public double angle(ISwitchE e, IVecI v){ return pos.angle(e,v); }
    public IDouble angle(ISwitchR r, IVecI v){ return pos.angle(r,v); }
    
    public double angle(IVecI v, IVecI axis){ return pos.angle(v,axis); }
    public double angle(double vx, double vy, double vz, double axisX, double axisY, double axisZ){
	return pos.angle(vx,vy,vz,axisX,axisY,axisZ);
    }
    public double angle(ISwitchE e, IVecI v, IVecI axis){ return pos.angle(e,v,axis); }
    public IDouble angle(ISwitchR r, IVecI v, IVecI axis){ return pos.angle(r,v,axis); }
    
    public IParticle rot(IDoubleI angle){ pos.rot(angle); return this; }
    public IParticle rot(double angle){ pos.rot(angle); return this; }
    
    public IParticle rot(IVecI axis, IDoubleI angle){ pos.rot(axis,angle); return this; }
    public IParticle rot(IVecI axis, double angle){ pos.rot(axis,angle); return this; }
    public IParticle rot(double axisX, double axisY, double axisZ, double angle){
	pos.rot(axisX,axisY,axisZ,angle); return this;
    }
    
    public IParticle rot(IVecI center, IVecI axis, double angle){
	pos.rot(center, axis,angle); return this;
    }
    public IParticle rot(IVecI center, IVecI axis, IDoubleI angle){
	pos.rot(center, axis,angle); return this;
    }
    public IParticle rot(double centerX, double centerY, double centerZ,
			 double axisX, double axisY, double axisZ, double angle){
	pos.rot(centerX,centerY,centerZ,axisX,axisY,axisZ,angle); return this;
    }
    
    /** Rotate to destination direction vector. */
    public IParticle rot(IVecI axis, IVecI destDir){ pos.rot(axis,destDir); return this; }
    /** Rotate to destination point location. */
    public IParticle rot(IVecI center, IVecI axis, IVecI destPt){
	pos.rot(center,axis,destPt); return this;
    }
    
    public IParticle rot2(IDoubleI angle){ return rot(angle); }
    public IParticle rot2(double angle){ return rot(angle); }
    public IParticle rot2(IVecI center, double angle){ pos.rot2(center,angle); return this; }
    public IParticle rot2(IVecI center, IDoubleI angle){ pos.rot2(center,angle); return this; }
    public IParticle rot2(double centerX, double centerY, double angle){
	pos.rot2(centerX,centerY,angle); return this;
    }
    
    /** Rotation on xy-plane to destination direction vector. */
    public IParticle rot2(IVecI destDir){ pos.rot2(destDir); return this; }
    /** Rotation on xy-plane to destination point location. */
    public IParticle rot2(IVecI center, IVecI destPt){ pos.rot2(center,destPt); return this; }
    
    
    /** alias of mul */
    public IParticle scale(IDoubleI f){ pos.scale(f); return this; }
    /** alias of mul */
    public IParticle scale(double f){ pos.scale(f); return this; }
    
    public IParticle scale(IVecI center, IDoubleI f){ pos.scale(center,f); return this; }
    public IParticle scale(IVecI center, double f){ pos.scale(center,f); return this; }
    public IParticle scale(double centerX, double centerY, double centerZ, double f){
	pos.scale(centerX,centerY,centerZ,f); return this;
    }
    
    /** scale only in 1 direction */
    public IParticle scale1d(IVecI axis, double f){ pos.scale1d(axis,f); return this; }
    public IParticle scale1d(IVecI axis, IDoubleI f){ pos.scale1d(axis,f); return this; }
    public IParticle scale1d(double axisX, double axisY, double axisZ, double f){
	pos.scale1d(axisX,axisY,axisZ,f); return this;
    }
    public IParticle scale1d(IVecI center, IVecI axis, double f){
	pos.scale1d(center,axis,f); return this;
    }
    public IParticle scale1d(IVecI center, IVecI axis, IDoubleI f){
	pos.scale1d(center,axis,f); return this;
    }
    public IParticle scale1d(double centerX, double centerY, double centerZ,
			     double axisX, double axisY, double axisZ, double f){
	pos.scale1d(centerX,centerY,centerZ,axisX,axisY,axisZ,f); return this;
    }
    
    
    /** reflect (mirror) 3 dimensionally to the other side of the plane */
    public IParticle ref(IVecI planeDir){ pos.ref(planeDir); return this; }
    /** reflect (mirror) 3 dimensionally to the other side of the plane */
    public IParticle ref(double planeX, double planeY, double planeZ){
	pos.ref(planeX,planeY,planeZ); return this;
    }
    /** reflect (mirror) 3 dimensionally to the other side of the plane */
    public IParticle ref(IVecI center, IVecI planeDir){
	pos.ref(center,planeDir); return this;
    }
    /** reflect (mirror) 3 dimensionally to the other side of the plane */
    public IParticle ref(double centerX, double centerY, double centerZ,
			 double planeX, double planeY, double planeZ){
	pos.ref(centerX,centerY,centerY,planeX,planeY,planeZ); return this;
    }
    /** reflect (mirror) 3 dimensionally to the other side of the plane */
    public IParticle mirror(IVecI planeDir){ pos.ref(planeDir); return this; }
    /** reflect (mirror) 3 dimensionally to the other side of the plane */
    public IParticle mirror(double planeX, double planeY, double planeZ){
	pos.ref(planeX,planeY,planeZ); return this;
    }
    /** reflect (mirror) 3 dimensionally to the other side of the plane */
    public IParticle mirror(IVecI center, IVecI planeDir){
	pos.ref(center,planeDir); return this;
    }
    /** reflect (mirror) 3 dimensionally to the other side of the plane */
    public IParticle mirror(double centerX, double centerY, double centerZ,
			    double planeX, double planeY, double planeZ){
	pos.ref(centerX,centerY,centerZ,planeX,planeY,planeZ); return this;
    }
    
    /** shear operation */
    public IParticle shear(double sxy, double syx, double syz,
			double szy, double szx, double sxz){
	pos.shear(sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IParticle shear(IDoubleI sxy, IDoubleI syx, IDoubleI syz,
			IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	pos.shear(sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IParticle shear(IVecI center, double sxy, double syx, double syz,
			double szy, double szx, double sxz){
	pos.shear(center,sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IParticle shear(IVecI center, IDoubleI sxy, IDoubleI syx, IDoubleI syz,
			IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	pos.shear(center,sxy,syx,syz,szy,szx,sxz); return this;
    }
    
    public IParticle shearXY(double sxy, double syx){ pos.shearXY(sxy,syx); return this; }
    public IParticle shearXY(IDoubleI sxy, IDoubleI syx){ pos.shearXY(sxy,syx); return this; }
    public IParticle shearXY(IVecI center, double sxy, double syx){
	pos.shearXY(center,sxy,syx); return this;
    }
    public IParticle shearXY(IVecI center, IDoubleI sxy, IDoubleI syx){
	pos.shearXY(center,sxy,syx); return this;
    }
    
    public IParticle shearYZ(double syz, double szy){ pos.shearYZ(syz,szy); return this; }
    public IParticle shearYZ(IDoubleI syz, IDoubleI szy){ pos.shearYZ(syz,szy); return this; }
    public IParticle shearYZ(IVecI center, double syz, double szy){
	pos.shearYZ(center,syz,szy); return this;
    }
    public IParticle shearYZ(IVecI center, IDoubleI syz, IDoubleI szy){
	pos.shearYZ(center,syz,szy); return this;
    }
    
    public IParticle shearZX(double szx, double sxz){ pos.shearZX(szx,sxz); return this; }
    public IParticle shearZX(IDoubleI szx, IDoubleI sxz){ pos.shearZX(szx,sxz); return this; }
    public IParticle shearZX(IVecI center, double szx, double sxz){
	pos.shearZX(center,szx,sxz); return this;
    }
    public IParticle shearZX(IVecI center, IDoubleI szx, IDoubleI sxz){
	pos.shearZX(center,szx,sxz); return this;
    }
    
    /** translate is alias of add() */
    public IParticle translate(double x, double y, double z){ pos.translate(x,y,z); return this; }
    public IParticle translate(IDoubleI x, IDoubleI y, IDoubleI z){ pos.translate(x,y,z); return this; }
    public IParticle translate(IVecI v){ pos.translate(v); return this; }
    
    
    public IParticle transform(IMatrix3I mat){ pos.transform(mat); return this; }
    public IParticle transform(IMatrix4I mat){ pos.transform(mat); return this; }
    public IParticle transform(IVecI xvec, IVecI yvec, IVecI zvec){
	pos.transform(xvec,yvec,zvec); return this;
    }
    public IParticle transform(IVecI xvec, IVecI yvec, IVecI zvec, IVecI translate){
	pos.transform(xvec,yvec,zvec,translate); return this;
    }
    
    
    /** mv() is alias of add() */
    public IParticle mv(double x, double y, double z){ return add(x,y,z); }
    public IParticle mv(IDoubleI x, IDoubleI y, IDoubleI z){ return add(x,y,z); }
    public IParticle mv(IVecI v){ return add(v); }
    
    // method name cp() is used as getting control point method in curve and surface but here used also as copy because of the priority of variable fitting of diversed users' mind set over the clarity of the code organization
    /** cp() is alias of dup() */ 
    public IParticle cp(){ return dup(); }
    
    /** cp() is alias of dup().add() */
    public IParticle cp(double x, double y, double z){ return dup().add(x,y,z); }
    public IParticle cp(IDoubleI x, IDoubleI y, IDoubleI z){ return dup().add(x,y,z); }
    public IParticle cp(IVecI v){ return dup().add(v); }
    
    
    
    // methods creating new instance // returns IParticle?, not IVec?
    // returns IVec, not IParticle (2011/10/12)
    //public IParticle diff(IVecI v){ return dup().sub(v); }
    public IVec dif(IVecI v){ return pos.dif(v); }
    public IVec dif(double vx, double vy, double vz){ return pos.dif(vx,vy,vz); }
    public IVec diff(IVecI v){ return dif(v); }
    public IVec diff(double vx, double vy, double vz){ return dif(vx,vy,vz); }
    //public IParticle mid(IVecI v){ return dup().add(v).div(2); }
    public IVec mid(IVecI v){ return pos.mid(v); }
    public IVec mid(double vx, double vy, double vz){ return pos.mid(vx,vy,vz); }
    //public IParticle sum(IVecI v){ return dup().add(v); }
    public IVec sum(IVecI v){ return pos.sum(v); }
    public IVec sum(double vx, double vy, double vz){ return pos.sum(vx,vy,vz); }
    //public IParticle sum(IVecI... v){ IParticle ret = this.dup(); for(IVecI vi: v) ret.add(vi); return ret; }
    public IVec sum(IVecI... v){ return pos.sum(v); }
    //public IParticle bisect(IVecI v){ return dup().unit().add(v.dup().unit()); }
    public IVec bisect(IVecI v){ return pos.bisect(v); }
    public IVec bisect(double vx, double vy, double vz){ return pos.bisect(vx,vy,vz); }
    
    
    /**
       weighted sum.
       @return IVec
    */
    //public IParticle sum(IVecI v2, double w1, double w2){ return dup().mul(w1).add(v2,w2); }
    public IVec sum(IVecI v2, double w1, double w2){ return pos.sum(v2,w1,w2); }
    //public IParticle sum(IVecI v2, double w2){ return dup().mul(1.0-w2).add(v2,w2); }
    public IVec sum(IVecI v2, double w2){ return pos.sum(v2,w2); }
    
    //public IParticle sum(IVecI v2, IDoubleI w1, IDoubleI w2){ return dup().mul(w1).add(v2,w2); }
    public IVec sum(IVecI v2, IDoubleI w1, IDoubleI w2){ return sum(v2,w1,w2); }
    
    //public IParticle sum(IVecI v2, IDoubleI w2){ return dup().mul(new IDouble(1.0).sub(w2)).add(v2,w2); }
    public IVec sum(IVecI v2, IDoubleI w2){ return sum(v2,w2); }
    
    
    /** alias of cross. (not unitized ... ?) */
    //public IParticle nml(IVecI v){ return cross(v); }
    public IVec nml(IVecI v){ return pos.nml(v); }
    public IVec nml(double vx, double vy, double vz){ return pos.nml(vx,vy,vz); }
    /** create normal vector from 3 points of self, pt1 and pt2 */
    //public IParticle nml(IVecI pt1, IVecI pt2){ return this.diff(pt1).cross(this.diff(pt2)).unit(); }
    public IVec nml(IVecI pt1, IVecI pt2){ return pos.nml(pt1,pt2); }
    public IVec nml(double vx1, double vy1, double vz1, double vx2, double vy2, double vz2){
	return pos.nml(vx1,vy1,vz1,vx2,vy2,vz2);
    }
    
    /** checking x, y, and z is valid number (not Infinite, nor NaN). */
    public boolean isValid(){ return pos.isValid(); }
    
    
    public String toString(){ return pos.toString(); }
    
    
}

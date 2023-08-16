package autopartsclient.util.Player;

import java.awt.Dimension;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class PlayerUtils {
	public static MinecraftClient mc = MinecraftClient.getInstance();
	
	public static void blinkToPos(Vec3d startPos, final BlockPos endPos, final double slack, final double[] pOffset) {
        double curX = startPos.x;
        double curY = startPos.y;
        double curZ = startPos.z;
        try {
            final double endX = endPos.getX() + 0.5;
            final double endY = endPos.getY() + 1.0;
            final double endZ = endPos.getZ() + 0.5;

            double distance = Math.abs(curX - endX) + Math.abs(curY - endY) + Math.abs(curZ - endZ);
            int count = 0;
            while (distance > slack) {
                distance = Math.abs(curX - endX) + Math.abs(curY - endY) + Math.abs(curZ - endZ);
                if (count > 120) {
                    break;
                }
                final double diffX = curX - endX;
                final double diffY = curY - endY;
                final double diffZ = curZ - endZ;
                final double offset = ((count & 0x1) == 0x0) ? pOffset[0] : pOffset[1];
                if (diffX < 0.0) {
                    if (Math.abs(diffX) > offset) {
                        curX += offset;
                    } else {
                        curX += Math.abs(diffX);
                    }
                }
                if (diffX > 0.0) {
                    if (Math.abs(diffX) > offset) {
                        curX -= offset;
                    } else {
                        curX -= Math.abs(diffX);
                    }
                }
                if (diffY < 0.0) {
                    if (Math.abs(diffY) > 0.25) {
                        curY += 0.25;
                    } else {
                        curY += Math.abs(diffY);
                    }
                }
                if (diffY > 0.0) {
                    if (Math.abs(diffY) > 0.25) {
                        curY -= 0.25;
                    } else {
                        curY -= Math.abs(diffY);
                    }
                }
                if (diffZ < 0.0) {
                    if (Math.abs(diffZ) > offset) {
                        curZ += offset;
                    } else {
                        curZ += Math.abs(diffZ);
                    }
                }
                if (diffZ > 0.0) {
                    if (Math.abs(diffZ) > offset) {
                        curZ -= offset;
                    } else {
                        curZ -= Math.abs(diffZ);
                    }
                }
                mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(curX, curY, curZ, true));
                ++count;
            }
        } catch (Exception e) {

        }
    }
	
	public static String getDimension() {
        switch (mc.world.getRegistryKey().getValue().getPath()) {
            case "the_nether":
                return "NETHER";
            case "the_end":
                return "END";
            default:
                return "OVERWORLD";
        }
    }
	
	public static void setSpeed(final double moveSpeed, double yVelocity, final float pseudoYaw, final double pseudoStrafe, final double pseudoForward) {
        double forward = pseudoForward;
        double strafe = pseudoStrafe;
        float yaw = pseudoYaw;
        if (pseudoForward != 0.0D) {
            if (pseudoStrafe > 0.0D) {
                yaw = pseudoYaw + (float)(pseudoForward > 0.0D ? -45 : 45);
            } else if (pseudoStrafe < 0.0D) {
                yaw = pseudoYaw + (float)(pseudoForward > 0.0D ? 45 : -45);
            }

            strafe = 0.0D;
            if (pseudoForward > 0.0D) {
                forward = 1.0D;
            } else if (pseudoForward < 0.0D) {
                forward = -1.0D;
            }
        }

        if (strafe > 0.0D) {
            strafe = 1.0D;
        } else if (strafe < 0.0D) {
            strafe = -1.0D;
        }

        double mx = Math.cos(Math.toRadians((double)(yaw + 90.0F)));
        double mz = Math.sin(Math.toRadians((double)(yaw + 90.0F)));
        double x = forward * moveSpeed * mx + strafe * moveSpeed * mz;
        double z = forward * moveSpeed * mz - strafe * moveSpeed * mx;
        mc.player.setVelocity(x, yVelocity, z);
    }
	
	public static void teleport(Vec3d endPos){
    	double dist = Math.sqrt(mc.player.squaredDistanceTo(endPos.getX(), endPos.getY(), endPos.getZ()));
    	double packetDist = 5;
    	double xtp, ytp, ztp = 0;
    	
    	if(dist> packetDist){
    		double nbPackets = Math.round(dist / packetDist + 0.49999999999) - 1;
    		xtp = mc.player.getX();
    		ytp = mc.player.getY();
    		ztp = mc.player.getZ();		
    		for (int i = 1; i < nbPackets;i++){		
    			double xdi = (endPos.getX() - mc.player.getX())/( nbPackets);	
    			xtp += xdi;
    			 
    			double zdi = (endPos.getZ() - mc.player.getZ())/( nbPackets);	
    			ztp += zdi;
    			 
    			double ydi = (endPos.getY() - mc.player.getY())/( nbPackets);	
    			ytp += ydi;
    			PlayerMoveC2SPacket.PositionAndOnGround packet= new PlayerMoveC2SPacket.PositionAndOnGround(xtp, ytp, ztp, true);
    			
    			mc.player.networkHandler.sendPacket(packet);
    		}
    		
    		mc.player.setPosition(endPos.getX() + 0.5, endPos.getY(), endPos.getZ() + 0.5);
    	}else{
    		mc.player.setPosition(endPos.getX(), endPos.getY(), endPos.getZ());
    	}
    }
	
	public static double distanceTo(Entity entity) {
        return distanceTo(entity.getX(), entity.getY(), entity.getZ());
    }

    public static double distanceTo(BlockPos blockPos) {
        return distanceTo(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    public static double distanceTo(Vec3d vec3d) {
        return distanceTo(vec3d.getX(), vec3d.getY(), vec3d.getZ());
    }

    public static double distanceTo(double x, double y, double z) {
        float f = (float) (mc.player.getX() - x);
        float g = (float) (mc.player.getY() - y);
        float h = (float) (mc.player.getZ() - z);
        return MathHelper.sqrt(f * f + g * g + h * h);
    }
    
    public static double distance(double x1, double y1, double z1, double x2, double y2, double z2) {
        double dX = x2 - x1;
        double dY = y2 - y1;
        double dZ = z2 - z1;
        return Math.sqrt(dX * dX + dY * dY + dZ * dZ);
    }
    
    public static double[] directionSpeed(double speed) {
        float forward = mc.player.input.movementForward;
        float side = mc.player.input.movementSideways;
        float yaw = mc.player.getYaw();

        if (forward != 0) {
            if (side > 0) {
                yaw += (forward > 0 ? -45 : 45);
            } else if (side < 0) {
                yaw += (forward > 0 ? 45 : -45);
            }
            side = 0;

            //forward = clamp(forward, 0, 1);
            if (forward > 0) {
                forward = 1;
            } else if (forward < 0) {
                forward = -1;
            }
        }

        final double sin = Math.sin(Math.toRadians(yaw + 90));
        final double cos = Math.cos(Math.toRadians(yaw + 90));
        final double posX = (forward * speed * cos + side * speed * sin);
        final double posZ = (forward * speed * sin - side * speed * cos);
        return new double[]{posX, posZ};
    }
}

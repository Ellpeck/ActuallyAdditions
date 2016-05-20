/*
 * This file ("MapGenRiverCaves.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.gen.cave;

import com.google.common.base.Objects;
import de.ellpeck.actuallyadditions.mod.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.MapGenCaves;

import javax.annotation.Nonnull;
import java.util.Random;

//This is hideous. It's mostly copied from MapGenCaves and changed slightly.
public class MapGenCustomCaves extends MapGenCaves{

    @Override
    protected void addRoom(long probablySeed, int chunkX, int chunkZ, @Nonnull ChunkPrimer primer, double x, double y, double z){
        this.addTunnel(probablySeed, chunkX, chunkZ, primer, x, y, z, 1.0F+this.rand.nextFloat()*15.0F, 0.0F, 0.0F, -1, -1, this.rand.nextDouble()*1.2F+0.25F);
    }

    @Override
    protected void addTunnel(long probablySeed, int chunkX, int chunkZ, @Nonnull ChunkPrimer primer, double x, double y, double z, float float1, float float2, float float3, int int1, int int2, double double4){
        double d0 = (double)(chunkX*16+8);
        double d1 = (double)(chunkZ*16+8);
        float f = 0.0F;
        float f1 = 0.0F;
        Random random = new Random(probablySeed);

        if(int2 <= 0){
            int i = this.range*16-16;
            int2 = i-random.nextInt(i/4);
        }

        boolean flag2 = false;

        if(int1 == -1){
            int1 = int2/2;
            flag2 = true;
        }

        int j = random.nextInt(int2/2)+int2/4;

        for(boolean flag = random.nextInt(6) == 0; int1 < int2; ++int1){
            double d2 = 1.5D+(double)(MathHelper.sin((float)int1*(float)Math.PI/(float)int2)*float1);
            double d3 = d2*double4;
            float f2 = MathHelper.cos(float3);
            float f3 = MathHelper.sin(float3);
            x += (double)(MathHelper.cos(float2)*f2);
            y += (double)f3;
            z += (double)(MathHelper.sin(float2)*f2);

            if(flag){
                float3 = float3*0.92F;
            }
            else{
                float3 = float3*0.7F;
            }

            float3 = float3+f1*0.1F;
            float2 += f*0.1F;
            f1 = f1*0.9F;
            f = f*0.75F;
            f1 = f1+(random.nextFloat()-random.nextFloat())*random.nextFloat()*2.0F;
            f = f+(random.nextFloat()-random.nextFloat())*random.nextFloat()*4.0F;

            if(!flag2 && int1 == j && float1 > 1.0F && int2 > 0){
                this.addTunnel(random.nextLong(), chunkX, chunkZ, primer, x, y, z, random.nextFloat()*0.5F+0.5F, float2-((float)Math.PI/2F), float3/3.0F, int1, int2, 1.0D);
                this.addTunnel(random.nextLong(), chunkX, chunkZ, primer, x, y, z, random.nextFloat()*0.5F+0.5F, float2+((float)Math.PI/2F), float3/3.0F, int1, int2, 1.0D);
                return;
            }

            if(flag2 || random.nextInt(4) != 0){
                double d4 = x-d0;
                double d5 = z-d1;
                double d6 = (double)(int2-int1);
                double d7 = (double)(float1+2.0F+16.0F);

                if(d4*d4+d5*d5-d6*d6 > d7*d7){
                    return;
                }

                if(x >= d0-16.0D-d2*2.0D && z >= d1-16.0D-d2*2.0D && x <= d0+16.0D+d2*2.0D && z <= d1+16.0D+d2*2.0D){
                    int k2 = MathHelper.floor_double(x-d2)-chunkX*16-1;
                    int k = MathHelper.floor_double(x+d2)-chunkX*16+1;
                    int l2 = MathHelper.floor_double(y-d3)-1;
                    int l = MathHelper.floor_double(y+d3)+1;
                    int i3 = MathHelper.floor_double(z-d2)-chunkZ*16-1;
                    int i1 = MathHelper.floor_double(z+d2)-chunkZ*16+1;

                    if(k2 < 0){
                        k2 = 0;
                    }

                    if(k > 16){
                        k = 16;
                    }

                    if(l2 < 1){
                        l2 = 1;
                    }

                    if(l > 248){
                        l = 248;
                    }

                    if(i3 < 0){
                        i3 = 0;
                    }

                    if(i1 > 16){
                        i1 = 16;
                    }

                    for(int j1 = k2; j1 < k; ++j1){
                        for(int k1 = i3; k1 < i1; ++k1){
                            for(int l1 = l+1; l1 >= l2-1; --l1){
                                if(l1 >= 0 && l1 < 256){
                                    if(l1 != l2-1 && j1 != k2 && j1 != k-1 && k1 != i3 && k1 != i1-1){
                                        l1 = l2;
                                    }
                                }
                            }
                        }
                    }

                        for(int j3 = k2; j3 < k; ++j3){
                            double d10 = ((double)(j3+chunkX*16)+0.5D-x)/d2;

                            for(int i2 = i3; i2 < i1; ++i2){
                                double d8 = ((double)(i2+chunkZ*16)+0.5D-z)/d2;
                                boolean flag1 = false;

                                if(d10*d10+d8*d8 < 1.0D){
                                    for(int j2 = l; j2 > l2; --j2){
                                        double d9 = ((double)(j2-1)+0.5D-y)/d3;

                                        if(d9 > -0.7D && d10*d10+d9*d9+d8*d8 < 1.0D){
                                            IBlockState iblockstate1 = primer.getBlockState(j3, j2, i2);
                                            IBlockState iblockstate2 = Objects.firstNonNull(primer.getBlockState(j3, j2+1, i2), BLK_AIR);

                                            this.digBlock(primer, j3, j2, i2, chunkX, chunkZ, flag1, iblockstate1, iblockstate2);
                                        }
                                    }
                                }
                            }
                        }

                        if(flag2){
                            break;
                        }
                    }
            }
        }
    }

    @Override
    protected boolean canReplaceBlock(IBlockState first, @Nonnull IBlockState second){
        return true;
    }

    @Override
    protected void digBlock(@Nonnull ChunkPrimer primer, int x, int y, int z, int chunkX, int chunkZ, boolean foundTop, @Nonnull IBlockState state, @Nonnull IBlockState up){
        net.minecraft.world.biome.Biome biome = this.worldObj.getBiomeGenForCoords(new BlockPos(x+chunkX*16, 0, z+chunkZ*16));
        IBlockState top = biome.topBlock;
        IBlockState filler = biome.fillerBlock;

        if(this.canReplaceBlock(state, up) || state.getBlock() == top.getBlock() || state.getBlock() == filler.getBlock()){
            primer.setBlockState(x, y, z, BLK_AIR);
        }
    }

    @Override
    protected void recursiveGenerate(World worldIn, int chunkX, int chunkZ, int int1, int int2, @Nonnull ChunkPrimer primer){
        int i = this.rand.nextInt(this.rand.nextInt(this.rand.nextInt(20)+1)+1);

        if(this.rand.nextInt(10) != 0){
            i = 0;
        }

        for(int j = 0; j < i; ++j){
            double d0 = (double)(chunkX*16+this.rand.nextInt(16));
            double d1 = (double)this.rand.nextInt(this.rand.nextInt(236)+16);
            double d2 = (double)(chunkZ*16+this.rand.nextInt(16));
            int k = 1;

            if(this.rand.nextInt(4) == 0){
                this.addRoom(this.rand.nextLong(), int1, int2, primer, d0, d1, d2);
                k += this.rand.nextInt(4);
            }

            for(int l = 0; l < k; ++l){
                float f = this.rand.nextFloat()*((float)Math.PI*2F);
                float f1 = (this.rand.nextFloat()-0.5F)*2.0F/8.0F;
                float f2 = this.rand.nextFloat()*2.0F+this.rand.nextFloat();

                if(this.rand.nextInt(10) == 0){
                    f2 *= this.rand.nextFloat()*this.rand.nextFloat()*3.0F+1.0F;
                }

                this.addTunnel(this.rand.nextLong(), int1, int2, primer, d0, d1, d2, f2, f, f1, 0, 0, this.rand.nextDouble()*2F+1F);
            }
        }
    }
}
